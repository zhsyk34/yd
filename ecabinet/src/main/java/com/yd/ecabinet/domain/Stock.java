package com.yd.ecabinet.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Data
public class Stock {
    private final String code;
    private final int count;
}
