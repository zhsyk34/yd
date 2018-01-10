package com.yd.ecabinet.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@RequiredArgsConstructor(staticName = "of")
@Getter
public final class Orders {
    private final String deviceType = "cabinet";

    private final String shopCode;
    private final String specCode;
}