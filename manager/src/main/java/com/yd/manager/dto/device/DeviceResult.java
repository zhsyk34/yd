package com.yd.manager.dto.device;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class DeviceResult {
    @JsonProperty("ret")
    private int code;
    private String msg;
    @JsonProperty("page")
    private int pageNumber;
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("size")
    private int total;

    private List<StoreDevice> list;

    //TODO
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        URL url = new URL("file:\\D:\\workspace\\yd\\manager\\src\\test\\java\\com\\yd\\manager\\service\\data.json");
        DeviceResult result = mapper.readValue(url, DeviceResult.class);
        System.out.println(result);

        StoreDevice storeDevice = Optional.ofNullable(result.getList())
                .map(sd -> sd.stream().collect(Collectors.toMap(StoreDevice::getStoreName, Function.identity())))
                .orElse(Collections.emptyMap())
                .get("湖里云创智谷");

        System.err.println(storeDevice);
    }

    //TODO
    public static void init(ObjectMapper mapper) throws IOException {
        CollectionType javaType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Person.class);
        List<Person> personList = mapper.readValue("", javaType);
        List<Person> list2 = mapper.readValue("", new
                TypeReference<List<Person>>() {
                });

    }

    class Person {
    }
}
