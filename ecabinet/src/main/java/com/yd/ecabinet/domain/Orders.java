package com.yd.ecabinet.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Getter
public final class Orders {
    private final String deviceType = "cabinet";

    @NonNull
    private final String shopCode;

    private final String specCode;

    private final String tids;

    public static Orders fromSpec(String shopCode, String specCode) {
        return new Orders(shopCode, specCode, null);
    }

    public static Orders fromTid(String shopCode, String tids) {
        return new Orders(shopCode, null, tids);
    }
}