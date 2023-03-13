package com.nuoian.common.database.handler;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.common.database.interceptor.Dict;
import com.nuoian.common.redis.service.RedisService;
import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @Author: 吴宇森
 * @Date: 2022/8/24 17:28
 * @Description: 字典注解切面
 * @Package: com.nuoian.common.database.handler
 * @Version: 1.0
 */
@Slf4j
@Component
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DictHandler implements Interceptor {

    private final RedisService redisService;
    @Value("${mybatis-plus.typeAliasesPackage}")
    private String typeAliasesPackage;

    public DictHandler(@Lazy RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        String p1 = "handleResultSets";
        if (p1.equals(invocation.getMethod().getName())) {
            if (result instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) result;
                for (int i = 0; i < arrayList.size(); i++) {
                    Object o = arrayList.get(i);
                    Class cla = o.getClass();
                    if (cla.getName().contains(typeAliasesPackage)) {
                        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
                        Field[] fields = cla.getDeclaredFields();
                        boolean flag = false;
                        for (Field field : fields) {
                            Dict[] fieldsAn = field.getAnnotationsByType(Dict.class);
                            for (Dict annotation : fieldsAn) {
                                String code = annotation.code();
                                List<JSONObject> cache = redisService.getCacheMapValue(GlobalConstants.DICT_CACHE_KEY, code);
                                String dictStr = null;
                                String valueKey = annotation.value();
                                String val = jsonObject.getString(valueKey);
                                if (StringUtils.isNotNull(val)&&StringUtils.isNotNull(cache)) {
                                    for (JSONObject object : cache) {
                                        String objval = object.getString("itemValue");
                                        if (val.equals(objval)) {
                                            dictStr = object.getString("itemText");
                                            break;
                                        }
                                    }
                                }
                                jsonObject.put(field.getName(), dictStr);
                                flag = true;
                            }
                        }
                        if (flag) {
                            arrayList.set(i, jsonObject.toJavaObject(cla));
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        log.warn(properties.toString());
    }
}
