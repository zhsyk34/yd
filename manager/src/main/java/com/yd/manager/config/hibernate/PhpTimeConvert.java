package com.yd.manager.config.hibernate;

import com.yd.manager.utils.TimeUtils;

import javax.persistence.AttributeConverter;
import java.time.LocalDateTime;

public class PhpTimeConvert implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime time) {
        return TimeUtils.seconds(time);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long seconds) {
        return TimeUtils.parseSecond(seconds);
    }
}
