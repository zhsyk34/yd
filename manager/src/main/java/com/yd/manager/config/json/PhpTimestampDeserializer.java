package com.yd.manager.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yd.manager.utils.TimeUtils;

import java.io.IOException;

/**
 * @see com.yd.manager.config.hibernate.PhpTimeConvert
 */
@Deprecated
public class PhpTimestampDeserializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(TimeUtils.format(TimeUtils.parseSecond(value)));
    }
}
