package com.yd.manager.dto;

import com.yd.manager.util.TimeUtils;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
public class AccessRecordDateDTO extends AccessRecordDTO {
    @NonNull
    private final String date;

    private AccessRecordDateDTO(@NonNull String date, long enterCount, long entrantCount, BigDecimal regRate) {
        super(enterCount, entrantCount, regRate);
        this.date = date;
    }

    public static AccessRecordDateDTO from(@NonNull String date, AccessRecordDTO dto) {
        return Optional.ofNullable(dto).map(d -> new AccessRecordDateDTO(date, d.getEnterCount(), d.getEntrantCount(), d.getRegRate())).orElse(new AccessRecordDateDTO(date, 0, 0, null));
    }

    public static AccessRecordDateDTO from(@NonNull LocalDate date, AccessRecordDTO dto) {
        return from(TimeUtils.format(date), dto);
    }
}
