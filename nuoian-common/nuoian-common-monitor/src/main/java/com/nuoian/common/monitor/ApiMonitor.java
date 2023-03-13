package com.nuoian.common.monitor;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.common.redis.service.DistributedLock;
import com.nuoian.common.redis.service.RedisService;
import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;
import com.nuoian.core.date.DateUtils;
import com.nuoian.core.jwt.JwtInfo;
import com.nuoian.core.jwt.JwtPayload;
import com.nuoian.core.jwt.JwtUtils;
import com.nuoian.common.utils.HttpUtils;
import com.nuoian.core.request.RequestUtils;
import com.nuoian.core.request.RestTemplateUtil;
import com.nuoian.core.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 17:38
 * @Description: 业务API监控
 * @Package: com.nuoian.common.monitor
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
@RefreshScope
public class ApiMonitor {

    public final RedisService redisService;

    private static final Long LOCK_TIME = 3600L;

    @Value("#{'${nuoian.monitor.exclude-function}'.split(',')}")
    public List<String> excludeFunction;

    @Value("${spring.application.name}")
    public String serviceName;

    static final String P_CUT_STR = "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)";
    private final DistributedLock distributedLock;
    /**
     * 存放方法被调用的次数O
     */
    ConcurrentHashMap<String, Long> methodCalledNumMap = new ConcurrentHashMap<>();

    public ApiMonitor(DistributedLock distributedLock, RedisService redisService) {
        this.distributedLock = distributedLock;
        this.redisService = redisService;
    }

    @Pointcut(value = P_CUT_STR)
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start("API监控任务启动");
        String targetClassName = point.getSignature().getDeclaringTypeName();
        String onlyMethodName = point.getSignature().getName();
        String methodName = targetClassName + "." + point.getSignature().getName();

        if (excludeFunction.contains(methodName)) {
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        HttpServletRequest request = RequestUtils.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headerJson = new JSONObject();
        while (headerNames.hasMoreElements()) {
            String hn = headerNames.nextElement();
            headerJson.put(hn, request.getHeader(hn));
        }
        SysBusinessMonitor monitorInfo = SysBusinessMonitor.getInstance();
        monitorInfo.setRequestDate(DateUtils.getNowDateTime());
        monitorInfo.setRequestHeader(headerJson.toJSONString());
        monitorInfo.setRequestIp(HttpUtils.getIpAddr(request));
        String userName = "未知用户";
        JwtPayload payload = JwtUtils.analysisBody(request);
        if (StringUtils.isNotNull(payload)) {
            userName = payload.getUserName();
        }
        String lockKey = methodName.replace(GlobalConstants.SPOT, GlobalConstants.BLANK) + GlobalConstants.UNDERLINE + userName + GlobalConstants.UNDERLINE + HttpUtils.getIpAddr(request);
        if (distributedLock.getDistributedLock(lockKey, LOCK_TIME)) {
            try {
                JSONObject params = new JSONObject();
                String[] names = ((CodeSignature) point.getSignature()).getParameterNames();
                Object[] values = point.getArgs();
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    Object value = values[i];
                    if (StringUtils.isBlank(name) || StringUtils.isNull(value)) {
                        continue;
                    }
                    params.put(name, value.toString());
                }
                monitorInfo.setRequestParams(params.toJSONString());
                monitorInfo.setUserName(userName);
                monitorInfo.setServiceName(serviceName);
                monitorInfo.setClassName(targetClassName);
                monitorInfo.setMethodName(onlyMethodName);
                Object result = point.proceed();
                if (StringUtils.isNull(result)) {
                    return JSONObject.toJSONString(ReturnResult.error());
                }
                monitorInfo.setSuccess(true);
                log.info("方法：{} 执行成功", targetClassName + "『" + onlyMethodName + "』");
                return result;
            } catch (Exception throwable) {
                monitorInfo.setSuccess(false);
                monitorInfo.setErrorStack(StringUtils.throwableStackExport(throwable));
                log.error("方法：{} 执行失败：", targetClassName + "『" + onlyMethodName + "』", throwable);
                return JSONObject.toJSONString(ReturnResult.error());
            } finally {
                if (methodCalledNumMap.containsKey(methodName)) {
                    methodCalledNumMap.put(methodName, methodCalledNumMap.get(methodName) + 1L);
                } else {
                    methodCalledNumMap.put(methodName, 1L);
                }
                log.info("当前方法【{}】被调用 {} 次",methodName,methodCalledNumMap.get(methodName));
                //释放redis分布式锁
                distributedLock.freedDistributedLock(lockKey);
                monitorInfo.setFinishDate(DateUtils.getNowDateTime());
                watch.stop();
                monitorInfo.setExecutionTime(Math.toIntExact(watch.getLastTaskTimeMillis()));
                String hashKey=System.currentTimeMillis()+StringUtils.mixtureCodeForBidAndSmall(6);
                redisService.hmSet(GlobalConstants.API_MONITOR_KEY,hashKey,monitorInfo);
             }
        }
        watch.stop();
        return point.proceed();
    }
}
