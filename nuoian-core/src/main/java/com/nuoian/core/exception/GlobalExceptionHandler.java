package com.nuoian.core.exception;

import com.nuoian.core.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/10 10:52
 * @Description: 全局异常拦截
 * @Package: com.nuoian.core.exception
 * @Version: 1.0
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class, Throwable.class})
    public ReturnResult exceptionResult(Exception e) {
        log.error("全局异常拦截：" + e.getMessage(), e);
        return ReturnResult.error();
    }
}
