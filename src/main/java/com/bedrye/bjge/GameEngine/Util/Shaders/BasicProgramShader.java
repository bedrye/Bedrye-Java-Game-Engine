package com.bedrye.bjge.GameEngine.Util.Shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;


import static java.lang.System.in;
import static org.lwjgl.opengl.GL11.GL_FALSE;

import static org.lwjgl.opengl.GL20.*;

public class BasicProgramShader extends ShaderProgram {
    private VertexShader vertexShader;
    private FragmentShader fragmentShader;


    public BasicProgramShader(String string,String name){
        super(string,name);
        String text ="";
        if (string.equals("INTERNAL")) {
            text = readInternal("/Internal/"+name);
            ArrayList<Shader> shaders = ShaderReader.getShadersFromString(text);
            vertexShader = (VertexShader) shaders.get(0);
            fragmentShader = (FragmentShader) shaders.get(1);
        }
        else {
            Path path = Path.of(string);
            try {
            text= new String(Files.readAllBytes(path));
                ArrayList<Shader> shaders = ShaderReader.getShadersFromString(text);
                vertexShader = (VertexShader) shaders.get(0);
                fragmentShader = (FragmentShader) shaders.get(1);
            }catch (IOException e){
                e.printStackTrace();
            }

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

    private String readInternal(String internalPath) {
        try (InputStream in = getClass().getResourceAsStream(internalPath)) {
            if (in == null) {
                throw new FileNotFoundException("Internal resource not found: " + internalPath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\r\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load internal resource: " + internalPath, e);
        }

    }

}
