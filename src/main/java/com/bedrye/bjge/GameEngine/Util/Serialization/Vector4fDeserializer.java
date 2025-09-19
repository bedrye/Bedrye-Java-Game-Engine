package com.bedrye.bjge.GameEngine.Util.Serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joml.Vector4f;

import java.io.IOException;

public class Vector4fDeserializer extends JsonDeserializer<Vector4f> {
    @Override
    public Vector4f deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        float x = (float) node.get("x").asDouble();
        float y = (float) node.get("y").asDouble();
        float z = (float) node.get("z").asDouble();
        float w = (float) node.get("w").asDouble();
        return new Vector4f(x, y, z, w);
    }
}
