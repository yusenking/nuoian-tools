package com.nuoian.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 14:36
 * @Description: XSS攻击过滤器
 * @Package: com.nuoian.core.filter
 * @Version: 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "XSSFiler", description = "信息安全审核")
public class XssFilter implements Filter {
    @SuppressWarnings("unused")
    private FilterConfig filterConfig;

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }


}
