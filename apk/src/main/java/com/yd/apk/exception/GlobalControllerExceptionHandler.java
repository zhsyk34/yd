package com.yd.apk.exception;

import com.yd.apk.domain.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(WebException.class)
    @ResponseBody
    public Result<String> handWebException(WebException e) {
        e.printStackTrace();
        return Result.error(e.getHttpStatus());
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