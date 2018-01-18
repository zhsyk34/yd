package com.yd.manager.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Device {
    @JsonProperty("chan_no")
    private int channelNo;
    private String name;
    private int state;
}
