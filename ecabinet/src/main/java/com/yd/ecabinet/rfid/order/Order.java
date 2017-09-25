package com.yd.ecabinet.rfid.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yd.ecabinet.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.yd.ecabinet.config.StoreConfig.STORE_NUMBER;
import static java.util.stream.Collectors.toSet;

@SuppressWarnings("WeakerAccess")
@Accessors(chain = true)
@Getter//TODO:static field
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
final class Order {

    private final String deviceType = "cabinet";

    private final String shopCode = STORE_NUMBER;

    /**
     * 如果为空则忽略此参数
     */
    @Setter
    private Set<String> tids;

    public static Order from(Set<String> tids) {
        return new Order().setTids(tids);
    }

    public static Map<String, Object> toMap(Set<String> tids) {
        return JsonUtils.toMap(from(Optional.ofNullable(tids).map(t -> t.stream().map(s -> "\"" + s + "\"").collect(toSet())).orElse(null)));
    }

    public static void main(String[] args) {
        System.out.println(toMap(null));
    }

}
