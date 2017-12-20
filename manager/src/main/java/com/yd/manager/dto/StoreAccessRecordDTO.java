package com.yd.manager.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StoreAccessRecordDTO extends AccessRecordDTO {
    private final long storeId;

    private StoreAccessRecordDTO(long storeId, long enterCount, long entrantCount, BigDecimal regRate) {
        super(enterCount, entrantCount, regRate);
        this.storeId = storeId;
    }

    public static StoreAccessRecordDTO from(long storeId, long enterCount, long entrantCount) {
        return new StoreAccessRecordDTO(storeId, enterCount, entrantCount, getRegRate(enterCount, entrantCount));
    }
}
