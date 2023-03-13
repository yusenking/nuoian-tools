package com.nuoian.core.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 11:01
 * @Description: Request工具类
 * @Package: com.nuoian.core.request
 * @Version: 1.0
 */
@Slf4j
public class RequestUtils {
    /**
     * 功能描述:
     * 〈任意位置获取request〉
     *
     * @return: javax.servlet.http.HttpServletRequest
     * @author: 吴宇森
     * @date: 2022/8/9 11:08
     */
    public static HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        } catch (Exception e) {
            log.error("任意位置获取Request异常：", e);
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈任意位置获取Response〉
     *
     * @return: javax.servlet.http.HttpServletResponse
     * @author: 吴宇森
     * @date: 2022/8/9 11:09
     */
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            log.error("任意位置获取Response异常：", e);
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈任意位置获取Session〉
     *
     * @return: javax.servlet.http.HttpSession
     * @author: 吴宇森
     * @date: 2022/8/9 11:15
     */
    public static HttpSession getSession() {
        try {
            return getRequestAttributes().getRequest().getSession();
        } catch (Exception e) {
            log.error("任意位置获取Session异常：", e);
            return null;
        }
    }

    private static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()));
    }
}
