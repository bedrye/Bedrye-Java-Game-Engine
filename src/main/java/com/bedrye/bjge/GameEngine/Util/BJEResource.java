package com.bedrye.bjge.GameEngine.Util;

public abstract class BJEResource {
    private final String path;

    public BJEResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
