package com.zzs.pet.common;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result notLoginExceptionHandler(NotLoginException e) {
        return Result.fail(403, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        if (log.isDebugEnabled()) {
            log.debug(e.getMessage());
        }
        return Result.fail(500, "服务器内部错误");
    }
}
