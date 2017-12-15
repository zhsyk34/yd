package com.yd.manager.dto.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ManagerInfo {
    private final long id;
    @JsonProperty("user_type")
    private final int type;
    @JsonProperty("shop_id")
    private final List<Long> stores;
}