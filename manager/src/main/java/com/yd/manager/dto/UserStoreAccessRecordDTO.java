package com.yd.manager.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserStoreAccessRecordDTO extends AccessRecordDTO {
    private final long userId;
    private final long storeId;

    private UserStoreAccessRecordDTO(long userId, long storeId, long enterCount, long entrantCount, BigDecimal regRate) {
        super(enterCount, entrantCount, regRate);
        this.userId = userId;
        this.storeId = storeId;
    }

    public static UserStoreAccessRecordDTO from(long userId, long storeId, long enterCount, long entrantCount) {
        return new UserStoreAccessRecordDTO(userId, storeId, enterCount, entrantCount, getRegRate(enterCount, entrantCount));
    }

    public static UserStoreAccessRecordDTO from(long userId, long storeId, long enterCount) {
        return from(userId, storeId, enterCount, 0);
    }
}
