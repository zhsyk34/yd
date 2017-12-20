package com.yd.manager.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 访问
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AccessRecordDTO {
    private final long enterCount;
    private final long entrantCount;
    private final BigDecimal regRate;

    public static AccessRecordDTO from(long enterCount, long entrantCount) {
        return new AccessRecordDTO(enterCount, entrantCount, getRegRate(enterCount, entrantCount));
    }

    static BigDecimal getRegRate(long enterCount, long entrantCount) {
        return enterCount <= 0 ? null : new BigDecimal((double) entrantCount / enterCount);
    }

}
