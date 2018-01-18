package com.yd.manager.dto.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManagerInfo {
    private long id;
    @JsonProperty("user_type")
    private int type;
    @JsonProperty("shop_id")
    private List<Long> stores;
}