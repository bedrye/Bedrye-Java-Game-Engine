package com.bedrye.Objects;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class BJEMesh {

    private int vaoID, vboID,eboID,elen;

    public BJEMesh(float[] vertexes,int[] elements){
        elen=elements.length;
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);


        FloatBuffer vertBuff = BufferUtils.createFloatBuffer(vertexes.length);
        vertBuff.put(vertexes).flip();

        vboID =glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER,vertBuff,GL_STATIC_DRAW);

        IntBuffer intBuffer = BufferUtils.createIntBuffer(elements.length);
        intBuffer.put(elements).flip();
        glVertexAttribPointer(0,3,GL_FLOAT,true,7*4,0);
        glEnableVertexAttribArray(0);

        eboID =glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL_STATIC_DRAW);

        int posSize =3;
        int colorSize=4;
        int vetexSizeInBytes =(posSize+colorSize+2)* Float.BYTES;
        glVertexAttribPointer(0,posSize,GL_FLOAT,true,vetexSizeInBytes,0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1,colorSize,GL_FLOAT,true,vetexSizeInBytes,posSize * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2,2,GL_FLOAT,true,vetexSizeInBytes,(posSize+colorSize)* Float.BYTES);
        glEnableVertexAttribArray(2);
    }
    public void Draw(){

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES,elen,GL_UNSIGNED_INT,0);

    }
    public void Clear(){



        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);


    }



}
