package com.nuoian.common.redis.aspect;


import com.nuoian.common.redis.service.DistributedLock;
import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;
import com.nuoian.core.result.ReturnResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class NoRepeatSubmitAspect {
    private static final Logger logger = LoggerFactory.getLogger(NoRepeatSubmitAspect.class);
    private static final String SUFFIX = "SUFFIX";

    @Autowired
    DistributedLock distibutedLock;

    /**
     * 横切点
     */
    @Pointcut("@annotation(noRepeatSubmit)")
    public void repeatPoint(NoRepeatSubmit noRepeatSubmit) {
    }

    /**
     *  接收请求，并记录数据
     */
    @Around(value = "repeatPoint(noRepeatSubmit)")
    public Object doBefore(ProceedingJoinPoint joinPoint, NoRepeatSubmit noRepeatSubmit) {
        String key = GlobalConstants.NO_REPEAT_LOCK_PREFIX + noRepeatSubmit.location();
        Object[] args = joinPoint.getArgs();
        String name = noRepeatSubmit.name();
        int argIndex = noRepeatSubmit.argIndex();
        String suffix;
        if (StringUtils.isBlank(name)) {
            suffix = String.valueOf(args[argIndex]);
        } else {
            Map<String, Object> keyAndValue = getKeyAndValue(args[argIndex]);
            Object valueObj = keyAndValue.get(name);
            if (valueObj == null) {
                suffix = SUFFIX;
            } else {
                suffix = String.valueOf(valueObj);
            }
        }
        key = key + ":" + suffix;
        int seconds = noRepeatSubmit.seconds();
        logger.info("lock key : " + key);
        if (!distibutedLock.getDistributedLock(key, (long)seconds)) {
            return ReturnResult.error(noRepeatSubmit.msg());
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("运行业务代码出错", throwable);
            // throw new RuntimeException(throwable.getMessage());
            return ReturnResult.error("业务处理错误");
        } finally {
            distibutedLock.freedDistributedLock(key);
        }
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                // 设置键值
                map.put(f.getName(), val);
            } catch (IllegalArgumentException e) {
                logger.error("getKeyAndValue IllegalArgumentException", e);
            } catch (IllegalAccessException e) {
                logger.error("getKeyAndValue IllegalAccessException", e);
            }

        }
        // logger.info("扫描结果：" + gson.toJson(map));
        return map;
    }
}
