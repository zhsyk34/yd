package com.yd.manager.controller;

import com.yd.manager.dto.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<String> handleThrowable(Throwable throwable) {
        return Result.from(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

}