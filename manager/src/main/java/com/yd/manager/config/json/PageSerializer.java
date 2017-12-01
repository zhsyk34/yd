package com.yd.manager.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PageSerializer extends StdSerializer<Page> {

    protected PageSerializer() {
        super(Page.class);
    }

    @Override
    public void serialize(Page value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("number", value.getNumber());
        gen.writeNumberField("size", value.getSize());
        gen.writeNumberField("total", value.getTotalPages());
        gen.writeObjectField("data", value.getContent());

        gen.writeEndObject();
    }
}