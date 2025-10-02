package com.bedrye.bjge.GameEngine.Util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BJETextFile extends BJEResource{
    public BJETextFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }
}
