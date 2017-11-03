package com.yd.apk.exception;

import lombok.*;
import org.springframework.http.*;

@RequiredArgsConstructor(staticName = "from")
@Getter
public class WebException extends RuntimeException {
    private final HttpStatus httpStatus;
}
