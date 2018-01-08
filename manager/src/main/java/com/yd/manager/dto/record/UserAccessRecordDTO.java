package com.yd.manager.dto.record;

import lombok.Getter;

@Getter
public class UserAccessRecordDTO extends AccessRecordDTO {
    private final long userId;

    private UserAccessRecordDTO(long userId, long enterCount, long entrantCount, long validCount) {
        super(enterCount, entrantCount, validCount);
        this.userId = userId;
    }

    public static UserAccessRecordDTO from(long userId, long enterCount, long entrantCount, long validCount) {
        return new UserAccessRecordDTO(userId, enterCount, entrantCount, validCount);
    }

}
