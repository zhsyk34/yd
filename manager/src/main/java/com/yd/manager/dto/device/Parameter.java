package com.yd.manager.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parameter {
    @JsonProperty("account")
    private String account;
    @JsonProperty("device_serial")
    private String deviceSerial;
    @JsonProperty("chan_no")
    private Integer channelNo;
    @JsonProperty("page_num")
    private Integer pageNumber;
    @JsonProperty("page_size")
    private Integer pageSize;
}