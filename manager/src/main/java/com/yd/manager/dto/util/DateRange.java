package com.yd.manager.dto.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@SuppressWarnings("WeakerAccess")
@Getter
@RequiredArgsConstructor(staticName = "of")
public class DateRange {
    private final LocalDate begin;
    private final LocalDate end;

    public static DateRange ofDate(LocalDate day) {
        return of(day, day);
    }

    public static DateRange rangeFrom(LocalDate begin) {
        return of(begin, LocalDate.now());
    }

    public static DateRange today() {
        return ofDate(LocalDate.now());
    }

    public static DateRange week() {
        return rangeFrom(LocalDate.now().minusWeeks(1).plusDays(1));
    }

    public static DateRange month() {
        return rangeFrom(LocalDate.now().minusMonths(1));
    }

    public static DateRange season() {
        return rangeFrom(LocalDate.now().minusMonths(3));
    }

    public TimeRange toTimeRange() {
        return TimeRange.of(Optional.ofNullable(begin).map(b -> LocalDateTime.of(b, LocalTime.MIN)).orElse(null), Optional.ofNullable(end).map(e -> LocalDateTime.of(e, LocalTime.MAX)).orElse(null));
    }
}
