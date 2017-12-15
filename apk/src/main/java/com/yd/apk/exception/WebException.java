package com.yd.apk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(staticName = "from")
@Getter
public class WebException extends RuntimeException {
    private final int value;

    private final String reasonPhrase;

    public static WebException from(HttpStatus httpStatus) {
        return from(httpStatus.value(), httpStatus.getReasonPhrase());
    }
}
