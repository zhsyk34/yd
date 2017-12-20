package com.yd.manager.dto;

import lombok.Data;

@Data
public class AccessRecordCount {
    private final long userOrStoreId;
    private final long count;
}
