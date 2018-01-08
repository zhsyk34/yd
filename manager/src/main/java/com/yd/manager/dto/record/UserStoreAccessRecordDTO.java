package com.yd.manager.dto.record;

import lombok.Getter;

@Getter
public class UserStoreAccessRecordDTO extends AccessRecordDTO {
    private final long userId;
    private final long storeId;
    private final String storeName;

    private UserStoreAccessRecordDTO(long userId, long storeId, String storeName, long enterCount, long entrantCount, long validCount) {
        super(enterCount, entrantCount, validCount);
        this.userId = userId;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public static UserStoreAccessRecordDTO from(long userId, long storeId, String storeName, long enterCount, long entrantCount, long validCount) {
        return new UserStoreAccessRecordDTO(userId, storeId, storeName, enterCount, entrantCount, validCount);
    }
}
