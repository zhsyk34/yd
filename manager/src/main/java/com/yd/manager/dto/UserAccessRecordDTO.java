package com.yd.manager.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserAccessRecordDTO extends AccessRecordDTO {
    private final long userId;

    private UserAccessRecordDTO(long userId, long enterCount, long entrantCount, BigDecimal regRate) {
        super(enterCount, entrantCount, regRate);
        this.userId = userId;
    }

    public static UserAccessRecordDTO from(long userId, long enterCount, long entrantCount) {
        return new UserAccessRecordDTO(userId, enterCount, entrantCount, getRegRate(enterCount, entrantCount));
    }
}
