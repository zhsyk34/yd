package com.yd.manager.dto.record;

import lombok.Getter;

@Getter
public class StoreAccessRecordDTO extends AccessRecordDTO {
    private final long storeId;

    private StoreAccessRecordDTO(long storeId, long enterCount, long entrantCount, long validCount) {
        super(enterCount, entrantCount, validCount);
        this.storeId = storeId;
    }

    public static StoreAccessRecordDTO from(long storeId, long enterCount, long entrantCount, long validCount) {
        return new StoreAccessRecordDTO(storeId, enterCount, entrantCount, validCount);
    }
}
