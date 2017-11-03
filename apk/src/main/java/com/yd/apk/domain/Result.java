package com.yd.apk.domain;

import lombok.*;
import org.springframework.http.*;

@SuppressWarnings({"WeakerAccess", "unused"})
@RequiredArgsConstructor(staticName = "of")
@Data
public class Result<T> {
    private final int code;
    private final String message;
    private final T data;

    public static <T> Result<T> from(HttpStatus status, T data) {
        return Result.of(status.value(), status.getReasonPhrase(), data);
    }

    public static <T> Result<T> from(HttpStatus status) {
        return from(status, null);
    }

    public static <T> Result<T> success(T data) {
        return from(HttpStatus.OK, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(HttpStatus status) {
        return from(status);
    }

}
