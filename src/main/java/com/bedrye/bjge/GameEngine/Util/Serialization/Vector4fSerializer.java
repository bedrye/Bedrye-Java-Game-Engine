package com.bedrye.bjge.GameEngine.Util.Serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joml.Vector4f;

import java.io.IOException;

public class Vector4fSerializer extends JsonSerializer<Vector4f> {
    @Override
    public void serialize(Vector4f value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("x", value.x);
        gen.writeNumberField("y", value.y);
        gen.writeNumberField("z", value.z);
        gen.writeNumberField("w", value.w);
        gen.writeEndObject();
    }
}
