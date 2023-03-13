package com.nuoian.common.database.handler;

import com.nuoian.common.database.config.DbContextHolder;
import com.nuoian.common.database.interceptor.ReadOnly;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/6 16:51
 * @Description: 自定义注解切面
 * @Package: com.nuoian.common.database.handler
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class ReadOnlyHandler implements Ordered {

    @Around("@annotation(readOnly)")
    public Object setReadOnly(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable{
        try{
            //当使用ReadOnly注解时将数据库类型设置为读
            DbContextHolder.setDbType(DbContextHolder.READ);
            return joinPoint.proceed();
        }finally {
            //清除dbType
            DbContextHolder.clearDbType();
        }
    }

    @Override
    public int getOrder() {
        //当前注解切面等级  0为最高
        return 0;
    }
}
