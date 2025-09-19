package com.bedrye.bjge.GameEngine.Util.Serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

// Deserializer
public class Vector3fDeserializer extends JsonDeserializer<Vector3f> {
    @Override
    public Vector3f deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        float x = (float) node.get("x").asDouble();
        float y = (float) node.get("y").asDouble();
        float z = (float) node.get("z").asDouble();
        return new Vector3f(x, y, z);
    }
}


