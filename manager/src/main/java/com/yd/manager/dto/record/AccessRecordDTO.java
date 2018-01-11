package com.yd.manager.dto.record;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 访问
 */
@Getter
public class AccessRecordDTO {
    static final AccessRecordDTO EMPTY = AccessRecordDTO.from(0, 0, 0);

    private final long enterCount;
    private final long entrantCount;
    private final long validCount;
    private BigDecimal regRate;

    protected AccessRecordDTO(long enterCount, long entrantCount, long validCount) {
        this.enterCount = enterCount;
        this.entrantCount = entrantCount;
        this.validCount = validCount;

        this.regRate = getRegRate(enterCount, entrantCount);
    }

    public static AccessRecordDTO from(long enterCount, long entrantCount, long validCount) {
        return new AccessRecordDTO(enterCount, entrantCount, validCount);
    }

    private static BigDecimal getRegRate(long enterCount, long entrantCount) {
        return enterCount <= 0 ? null : new BigDecimal((double) entrantCount / enterCount);
    }

}
