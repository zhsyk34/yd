package com.yd.manager.dto.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class TimeRange {
    private final LocalDateTime begin;
    private final LocalDateTime end;
}
