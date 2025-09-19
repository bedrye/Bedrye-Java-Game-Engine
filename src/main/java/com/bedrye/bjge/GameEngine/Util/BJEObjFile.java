package com.bedrye.bjge.GameEngine.Util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BJEObjFile extends BJEResource{

    public BJEObjFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }
}
