package com.bedrye.bjge.GameEngine.Util.Serialization;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.joml.Vector3f;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class Vector3fSerializer extends JsonSerializer<Vector3f> {
    @Override
    public void serialize(Vector3f value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("x", value.x);
        gen.writeNumberField("y", value.y);
        gen.writeNumberField("z", value.z);
        gen.writeEndObject();
    }
}

