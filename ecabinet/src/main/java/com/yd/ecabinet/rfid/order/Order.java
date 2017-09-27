package com.yd.ecabinet.rfid.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yd.ecabinet.util.JsonUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.yd.ecabinet.config.StoreConfig.DEVICE_TYPE;
import static com.yd.ecabinet.config.StoreConfig.NUMBER;
import static java.util.stream.Collectors.toSet;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

@RequiredArgsConstructor(staticName = "of")
@Getter
final class Order {

    private final String deviceType = DEVICE_TYPE;

    private final String shopCode = NUMBER;

    /**
     * 如果为空则忽略此参数
     */
    private final Set<String> tids;

    static Map<String, Object> toMap(Set<String> tids) {
        return JsonUtils.toMap(of(Optional.ofNullable(tids).map(t -> t.stream().map(s -> "\"" + s + "\"").collect(toSet())).orElse(null)));
    }

}
