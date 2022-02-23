package com.nuoian.tools.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 16:48
 * @Description: MVC配置类
 * @Package: com.nuoian.tools.interceptor
 * @Version: 1.0
 */
@SpringBootConfiguration
public class SpringMvcConfig implements WebMvcConfigurer {
    private final CrossInterceptor crossInterceptor;

    public SpringMvcConfig(CrossInterceptor crossInterceptor) {
        this.crossInterceptor = crossInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
    }
}
