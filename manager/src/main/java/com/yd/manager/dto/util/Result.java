package com.yd.manager.dto.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
@Data
public class Result<T> {
    private final int code;
    private final String message;
    private final T data;

    public static <T> Result<T> from(int code, String message) {
        return Result.of(code, message, null);
    }

    public static <T> Result<T> from(HttpStatus status, T t) {
        return Result.of(status.value(), status.getReasonPhrase(), t);
    }

    public static <T> Result<T> success(T t) {
        return Result.from(HttpStatus.OK, t);
    }

    public static <T> Result<T> success() {
        return Result.success(null);
    }

    public static <T> Result<T> error(HttpStatus status) {
        return Result.from(status, null);
    }

    public static <T> Result<T> error() {
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE);
    }

}
