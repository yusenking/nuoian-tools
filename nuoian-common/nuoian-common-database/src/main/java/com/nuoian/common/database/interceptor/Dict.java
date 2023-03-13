package com.nuoian.common.database.interceptor;

import java.lang.annotation.*;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/24 17:22
 * @Description: 字典注解
 * @Package: com.nuoian.common.database.interceptor
 * @Version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {
    String code();

    String value();

}
