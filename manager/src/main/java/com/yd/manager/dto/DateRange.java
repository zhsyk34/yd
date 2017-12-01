package com.yd.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.Optional;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class DateRange {
    private final LocalDate begin;
    private final LocalDate end;

    public static DateRange day(LocalDate day) {
        return of(day, day);
    }

    public static DateRange range(LocalDate begin) {
        return of(begin, LocalDate.now());
    }

    public static DateRange week() {
        return range(LocalDate.now().minusWeeks(1).plusDays(1));
    }

    public static DateRange month() {
        return range(LocalDate.now().minusMonths(1));
    }

    public static DateRange season() {
        return range(LocalDate.now().minusMonths(3));
    }

    public TimeRange toTimeRange() {
        return TimeRange.of(Optional.ofNullable(begin).map(b -> LocalDateTime.of(b, LocalTime.MIN)).orElse(null), Optional.ofNullable(end).map(e -> LocalDateTime.of(e, LocalTime.MAX)).orElse(null));
    }
}
