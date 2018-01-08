package com.yd.manager.dto.record;

import lombok.Data;

@Data
public class AccessRecordCount {
    private final long userOrStoreId;
    private final long count;
}
