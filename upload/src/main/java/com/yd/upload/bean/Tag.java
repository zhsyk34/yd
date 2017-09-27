package com.yd.upload.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yd.upload.util.JsonUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.yd.upload.config.StoreConfig.DEVICE_TYPE;
import static com.yd.upload.config.StoreConfig.MAC;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Tag {

    private final String deviceType = DEVICE_TYPE;

    private final String rfidCode = MAC;

    private final String tid;

    public static Map<String, Object> from(String tid) {
        Tag tag = Tag.of(tid);
        return JsonUtils.toMap(tag);
    }

}
