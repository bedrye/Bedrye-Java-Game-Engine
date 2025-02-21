package com.bedrye.Objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
//TODO BATCHING
public class BJEMesh {
    private final int posSize =3;
    private final int colorSize=4;
    private final int UVSize=2;
    private final int vetexSizeInBytes =(posSize+colorSize+UVSize)* Float.BYTES;
    public float[] getVertexes() {
        return vertexes;
    }

    public void setVertexesPosition(Matrix4f matrix4f) {

        for (int i = 0; i < vertexes.length ; i+=9) {
            Matrix4f mat = new Matrix4f(matrix4f);
            Vector4f vec4 = new Vector4f(loc_vertexes[i],loc_vertexes[i+1],loc_vertexes[i+2],1.0f).mul(mat);
            vertexes[i] = vec4.x;
            vertexes[i+1] = vec4.y;
            vertexes[i+2] = vec4.z;
        }
        setup();
    }

    public void setVertexes(float[] vertexes) {
        this.vertexes = vertexes;
    }

    public int[] getElements() {
        return elements;
    }

    public void setElements(int[] elements) {
        this.elements = elements;
    }

    private float[] vertexes;
    private final float[] loc_vertexes;
    private int[] elements;
    private int vaoID, vboID,eboID,elen;

    public void setup(){
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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0,posSize,GL_FLOAT,true,vetexSizeInBytes,0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1,colorSize,GL_FLOAT,true,vetexSizeInBytes,posSize * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2,UVSize,GL_FLOAT,true,vetexSizeInBytes,(posSize+colorSize)* Float.BYTES);
        glEnableVertexAttribArray(2);
    }
    public BJEMesh(float[] verts,int[] e){
        this.loc_vertexes = verts;
        this.vertexes = verts.clone();
        this.elements = e;
        elen=elements.length;
    }
    public void Draw(){

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glTranslatef(10,10,10);
        glDrawElements(GL_TRIANGLES,elen,GL_UNSIGNED_INT,0);

    }
    public void Clear(){



        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);


    }



}
