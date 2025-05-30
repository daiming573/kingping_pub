package com.common.cache.redis.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class NullValueSerializer extends StdSerializer<NullValue> {
    private static final long serialVersionUID = 1999052150548658808L;
    private final String classIdentifier;

    NullValueSerializer(String classIdentifier) {
        super(NullValue.class);
        this.classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
    }

    @Override
    public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField(this.classIdentifier, NullValue.class.getName());
        jgen.writeEndObject();
    }
}