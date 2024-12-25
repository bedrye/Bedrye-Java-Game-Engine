package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GameScene extends Scene {

    private String vertexShader= "#version 330 core\n" +
                    "layout (location=0) in vec3 ShPos;\n" +
                    "layout (location=1) in vec4 ShColor;\n" +
                    "\n" +
                    "out vec4 fColor;\n" +
                    "\n" +
                    "void main(){\n" +
                    "        fColor=ShColor;\n" +
                    "        gl_Position = vec4(ShPos,1.0);\n" +
                    "}";

    private String fragmentShader="#version 330 core\n" +
            "in vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "        color=fColor;\n" +
            "}";

    private int vertexID,fragmentID,shaderProgram;
    private float[] vertexes ={
            0.5f,-0.5f,0.0f,   1.0f,0.0f,0.0f,1.0f,
            -0.5f,0.5f,0.0f,   0.0f,0.5f,0.5f,1.0f,
            0.5f,0.5f,0.0f,   0.0f,0.5f,0.0f,1.0f,
            -0.5f,-0.5f,0.0f,   0.0f,0.0f,0.5f,1.0f
    };
    private int vaoID,vbiID,eboID;


    private int[] elements = {
        2,1,0, 0,1,3
    };


    @Override
    public void initialize() {
       vertexID = glCreateShader(GL_VERTEX_SHADER);
       glShaderSource(vertexID,vertexShader);
       glCompileShader(vertexID);
       int errorInt = glGetShaderi(vertexID,GL_COMPILE_STATUS);
       if (errorInt==GL_FALSE){
           int l= glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
           System.out.println(glGetShaderInfoLog(vertexID,l));
           return;
       }
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID,fragmentShader);
        glCompileShader(fragmentID);
        errorInt = glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if (errorInt==GL_FALSE){
            int l= glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(fragmentID,l));
            return;
        }
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram,fragmentID);
        glLinkProgram(shaderProgram);

        errorInt = glGetProgrami(shaderProgram,GL_LINK_STATUS);
        if (errorInt==GL_FALSE){
            int l= glGetProgrami(shaderProgram,GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(shaderProgram,l));
            return;
        }

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        FloatBuffer vertBuff = BufferUtils.createFloatBuffer(vertexes.length);
        vertBuff.put(vertexes).flip();

        vbiID =glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER,vbiID);
        glBufferData(GL_ARRAY_BUFFER,vertBuff,GL_STATIC_DRAW);

        IntBuffer intBuffer = BufferUtils.createIntBuffer(elements.length);

        intBuffer.put(elements).flip();

        eboID =glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL_STATIC_DRAW);

        int PosSize =3;
        int ColorSize=4;
        int FloatSizeInBytes=4;
        int vetexSizeinBytes =(PosSize+ColorSize)*FloatSizeInBytes;
        glVertexAttribPointer(0,PosSize,GL_FLOAT,true,vetexSizeinBytes,0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1,ColorSize,GL_FLOAT,true,vetexSizeinBytes,PosSize * FloatSizeInBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void initialize(Vec3 position) {




    }

    @Override
    public void update() {
        glClearColor(0.8f,0.3f,0.8f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glUseProgram(shaderProgram);
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES,elements.length,GL_UNSIGNED_INT,0);


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glUseProgram(0);



    }

}
