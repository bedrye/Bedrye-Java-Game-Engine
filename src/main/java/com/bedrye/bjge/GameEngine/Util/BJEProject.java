package com.bedrye.bjge.GameEngine.Util;

public class BJEProject {
    private String name;


    private String path;

    public BJEProject( String path ,String name) {
        this.name = name;
        this.path = path;
    }
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

}
