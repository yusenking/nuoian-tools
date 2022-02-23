package com.nuoian.tools.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 16:44
 * @Description: 跨域拦截器
 * @Package: com.nuoian.tools.interceptor
 * @Version: 1.0
 */
@Component
public class CrossInterceptor implements HandlerInterceptor {
    private List<String> excludedPath;

    public List<String> getExcludedPath() {
        return excludedPath;
    }

    public void setExcludedPath(List<String> excludedPath) {
        this.excludedPath = excludedPath;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Access-Token");
        response.setHeader("Access-Control-Max-Age", "3600");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
