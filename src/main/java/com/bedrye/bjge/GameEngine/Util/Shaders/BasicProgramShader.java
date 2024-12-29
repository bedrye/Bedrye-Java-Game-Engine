package com.bedrye.bjge.GameEngine.Util.Shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


import static org.lwjgl.opengl.GL11.GL_FALSE;

import static org.lwjgl.opengl.GL20.*;

public class BasicProgramShader extends ShaderProgram {
    private VertexShader vertexShader;
    private FragmentShader fragmentShader;


    public BasicProgramShader(Path path){
        super(path);
        try {
            String text= new String(Files.readAllBytes(path));
            ArrayList<Shader> shaders = ShaderReader.getShadersFromString(text);
            vertexShader = (VertexShader) shaders.get(0);
            fragmentShader = (FragmentShader) shaders.get(1);


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void Run() {
        glUseProgram(getProgramId());
    }

    @Override
    public void Compile() {
        fragmentShader.Compile();
        vertexShader.Compile();
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
