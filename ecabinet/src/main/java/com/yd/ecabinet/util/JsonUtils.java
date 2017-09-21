package com.yd.ecabinet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.ecabinet.rfid.order.Order;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String toJson(T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <K, V> Map<K, V> toMap(Object object) {
        return MAPPER.convertValue(object, Map.class);
    }

    public static void main(String[] args) {
        Set<String> tids = new HashSet<>(Arrays.asList("E280110020007783F1DD090C", "E28011002000754DF20A090C", "E280110020007783F1DD090C"));
        System.out.println(Order.toMap(tids));
//        MAPPER.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
//        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

//        Order order = new Order();
//
//        order.setTids(new HashSet<>());
//
//        System.out.println(toMap(order));
//
//        String json = toJson(order);
//        System.out.println(json);
//
//        System.out.println(parseJson(json, Order.class));
    }

}
