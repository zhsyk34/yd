package com.yd.manager.dto.record;

import com.yd.manager.util.TimeUtils;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class AccessRecordDateDTO extends AccessRecordDTO {
    @NonNull
    private final String date;

    private AccessRecordDateDTO(String date, long enterCount, long entrantCount, long validCount) {
        super(enterCount, entrantCount, validCount);
        this.date = date;
    }

    public static AccessRecordDateDTO from(@NonNull String date, AccessRecordDTO dto) {
        return Optional.ofNullable(dto).map(d -> new AccessRecordDateDTO(date, d.getEnterCount(), d.getEntrantCount(), d.getValidCount())).orElse(new AccessRecordDateDTO(date, 0, 0, 0));
    }

    public static AccessRecordDateDTO from(@NonNull LocalDate date, AccessRecordDTO dto) {
        return from(TimeUtils.format(date), dto);
    }

}
