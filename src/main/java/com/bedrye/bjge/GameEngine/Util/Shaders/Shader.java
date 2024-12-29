package com.bedrye.bjge.GameEngine.Util.Shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public abstract class Shader {


    private String shader;
    private int shaderId;


    public Shader(String shader){
        this.shader=shader;

    }
    public abstract void Compile();


    public int getShaderId() {
        return shaderId;
    }

    public void setShaderId(int shaderId) {
        this.shaderId = shaderId;
    }

    public String getShader() {
        return shader;
    }

    public void setShader(String shader) {
        this.shader = shader;
    }
}
