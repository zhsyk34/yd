package com.yd.manager.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StoreDevice {
    @JsonProperty("nick_name")
    private String storeName;
    private int state;
    private int type;

    @JsonProperty("device_no")
    private long deviceNumber;
    @JsonProperty("device_serial")
    private String deviceSerial;
    @JsonProperty("device_type_name")
    private String deviceType;

    private List<Device> list;
}
