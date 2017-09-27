package com.yd.upload.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@SuppressWarnings("unused")
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
        return MAPPER.convertValue(object, new TypeReference<Map<K, V>>() {
        });
    }

}
