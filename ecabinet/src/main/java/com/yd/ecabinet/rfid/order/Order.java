package com.yd.ecabinet.rfid.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yd.ecabinet.config.Config;
import com.yd.ecabinet.util.HttpUtils;
import com.yd.ecabinet.util.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Data
@Accessors(chain = true)

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {

    private final String deviceType = "cabinet";

    private final String shopCode = Config.STORE_SHOP;

    /**
     * 如果为空则忽略此参数
     */
    private Set<String> tids;

    @SuppressWarnings("WeakerAccess")
    public static Order from(Set<String> tids) {
        return new Order().setTids(tids);
    }

    public static Map<String, Object> toMap(Set<String> tids) {
        tids = Optional.ofNullable(tids).map(t -> t.stream().map(s -> "\"" + s + "\"").collect(toSet())).orElse(null);
        return JsonUtils.toMap(from(tids));
    }

    //TODO
    public static void main(String[] args) {
        Set<String> set = new HashSet<>(Arrays.asList("a", "b"));
        Map<String, Object> map = toMap(set);
        System.err.println(HttpUtils.postForm(Config.STORE_SERVER, map));
    }

}
