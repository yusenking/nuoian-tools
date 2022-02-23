package com.nuoian.tools.exception;

import com.nuoian.tools.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 17:28
 * @Description: 自定义全局异常处理
 * @Package: com.nuoian.tools.exception
 * @Version: 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalException {

    /**
     * 功能描述:
     * 〈全局异常处理〉
     * @param request 请求信息
     * @param exception 错误
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:33
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnResult exceptionHandler(HttpServletRequest request,Exception exception){
        log.error("全局异常捕获",exception);
        return ReturnResult.error(exception.getMessage());
    }
}
