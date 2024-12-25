package com.bedrye.bjge.GameEngine.Util.Shaders;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import static org.lwjgl.opengl.GL20.*;

public class BasicShader extends ShaderProgram {

    private VertexShader vertexShader;
    private FragmentShader fragmentShader;

    public BasicShader(){

    }

    @Override
    public void Run() {
        glUseProgram(getProgramId());
    }

    @Override
    public void Compile() {
        setProgramId(glCreateProgram());
        glAttachShader(getProgramId(), vertexShader.getShaderId());
        glAttachShader(getProgramId(), fragmentShader.getShaderId());
        glLinkProgram(getProgramId());

        int errorInt = glGetProgrami(getProgramId(),GL_LINK_STATUS);
        if (errorInt==GL_FALSE){
            int l= glGetProgrami(getProgramId(),GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(getProgramId(),l));
            return;
        }

    }

    @Override
    public void Clear() {
        glUseProgram(0);
    }




}
