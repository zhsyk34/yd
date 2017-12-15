package com.yd.apk.exception;

import com.yd.apk.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(WebException.class)
    @ResponseBody
    public Result<String> handWebException(WebException e) {
        return Result.of(e.getValue(), e.getReasonPhrase(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<String> handRuntimeException(Exception e) {
        e.printStackTrace();
        return Result.from(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<String> handleThrowable(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        return Result.from(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

}