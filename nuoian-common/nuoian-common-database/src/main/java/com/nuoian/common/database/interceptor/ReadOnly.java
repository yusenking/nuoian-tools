package com.nuoian.common.database.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/6 16:51
 * @Description: 自定义读注解
 * @Package: com.nuoian.common.database.interceptor
 * @Version: 1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {
}
