package com.yd.manager.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

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
}
