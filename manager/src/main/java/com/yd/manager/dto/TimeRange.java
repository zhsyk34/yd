package com.yd.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.Optional;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class TimeRange {
    private final LocalDateTime begin;
    private final LocalDateTime end;

    public static TimeRange from(LocalDate begin, LocalDate end) {
        return TimeRange.of(Optional.ofNullable(begin).map(b -> LocalDateTime.of(b, LocalTime.MIN)).orElse(null), Optional.ofNullable(end).map(e -> LocalDateTime.of(e, LocalTime.MAX)).orElse(null));
    }
}
