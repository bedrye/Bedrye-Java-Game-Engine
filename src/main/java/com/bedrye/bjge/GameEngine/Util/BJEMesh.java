package com.bedrye.bjge.GameEngine.Util;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.memFree;

//TODO BATCHING
public class BJEMesh {

    private ArrayList<BJEVertex> BJEVerteciesList = new ArrayList<>();

    public float[] getVertexes() {
        return vertexes;
    }

    public void setVertexesPosition(Matrix4f matrix4f) {





        upload();
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

    private int[] elements;
    private int vaoID, vboID,eboID, FaceBuffLen;
    FloatBuffer vertBuff;
    IntBuffer intBuffer;

    public void upload(){

        //vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);


        vertBuff.clear();
        vertBuff.put(vertexes).flip();

        //vboID =glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER,vertBuff,GL_DYNAMIC_DRAW);


        intBuffer.clear();
        intBuffer.put(elements).flip();
        glVertexAttribPointer(0,3,GL_FLOAT,true,7*4,0);
        glEnableVertexAttribArray(0);

        //eboID =glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0,3,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,0);
        glEnableVertexAttribArray(0);
        //Colors
        glVertexAttribPointer(1,4,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        //UV
        glVertexAttribPointer(2,2,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,7* Float.BYTES);
        glEnableVertexAttribArray(2);
        //Normal
        glVertexAttribPointer(3,3,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,9* Float.BYTES);
        glEnableVertexAttribArray(3);

    }
    public void setup(){
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);


        vertBuff = BufferUtils.createFloatBuffer(vertexes.length);
        vertBuff.put(vertexes).flip();

        vboID =glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER,vertBuff,GL_DYNAMIC_DRAW);


        intBuffer = BufferUtils.createIntBuffer(elements.length);

        intBuffer.put(elements).flip();
        glVertexAttribPointer(0,3,GL_FLOAT,true,7*4,0);
        glEnableVertexAttribArray(0);

        eboID =glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0,3,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,0);
        glEnableVertexAttribArray(0);
        //Colors
        glVertexAttribPointer(1,4,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        //UV
        glVertexAttribPointer(2,2,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,7* Float.BYTES);
        glEnableVertexAttribArray(2);
        //Normal
        glVertexAttribPointer(3,3,GL_FLOAT,true,BJEVertex.SIZE*Float.BYTES,9* Float.BYTES);
        glEnableVertexAttribArray(3);



    }
    public BJEMesh(float[] vertices, int[] e){


        this.vertexes = vertices.clone();
        this.elements = e;
        FaceBuffLen =elements.length;
        for (int i = 0; i < vertexes.length; i+=BJEVertex.SIZE) {
            System.out.println(vertexes[i]+ " "+vertexes[i+1]+ " "+vertexes[i+2]);


        }
    }
    public BJEMesh(ArrayList<BJEVertex> verts ,int[] e){

        BJEVerteciesList = verts;
        vertexes = new float[BJEVerteciesList.size()*BJEVertex.SIZE];
        for (int i = 0; i < BJEVerteciesList.size()*BJEVertex.SIZE; i+=BJEVertex.SIZE) {
            float[] buff = BJEVerteciesList.get(i/BJEVertex.SIZE).GetBuffer();
            for (int j = 0; j < BJEVertex.SIZE; j++) {
                vertexes[i+j]=buff[j];
            }
        }

        elements = e;
        FaceBuffLen = elements.length;
        for (BJEVertex ver: BJEVerteciesList
        ) {
            System.out.println(ver.toString());
        }
        for (int i = 0; i < BJEVerteciesList.size()*BJEVertex.SIZE; i+=BJEVertex.SIZE) {
            System.out.println(vertexes[i]+ " "+vertexes[i+1]+ " "+vertexes[i+2]);


        }
    }
    public BJEMesh(BJEResource resource){

        AIScene scene = Assimp.aiImportFile(resource.getPath(),Assimp.aiProcess_JoinIdenticalVertices|Assimp.aiProcess_Triangulate);
        assert scene!=null;
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        AIVector3D.Buffer vertecies = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();
        int vertCount = mesh.mNumVertices();

        for (int i = 0; i < vertCount; i++) {

            AIVector3D vertexCoords = vertecies.get(i);
            AIVector3D texture = mesh.mTextureCoords(0).get(i);
            AIVector3D normal = normals.get(i);
            Vector4f color = new Vector4f(1.0f,1.0f,1.0f,1.0f);
            if(mesh.mColors(0)!=null) {
                AIColor4D color4D = mesh.mColors(0).get(i);
                new Vector4f(color4D.r(), color4D.g(), color4D.b(), color4D.a());
            }


            BJEVerteciesList.add(new BJEVertex(
                    new Vector3f(vertexCoords.x(),vertexCoords.y(),vertexCoords.z()),
                    color,new Vector2f(texture.x(),texture.y())
                    ,new Vector3f(normal.x(),normal.y(),normal.z())
            ));
        }
        vertexes = new float[BJEVerteciesList.size()*BJEVertex.SIZE];
        for (int i = 0; i < BJEVerteciesList.size()*BJEVertex.SIZE; i+=BJEVertex.SIZE) {
            float[] buff = BJEVerteciesList.get(i/BJEVertex.SIZE).GetBuffer();
            for (int j = 0; j < BJEVertex.SIZE; j++) {
                vertexes[i+j]=buff[j];
            }
        }
        FaceBuffLen = mesh.mNumFaces()*3;
        AIFace.Buffer faces = mesh.mFaces();
        elements = new int[FaceBuffLen];
        for (int i = 0; i < FaceBuffLen; i+=3) {
            AIFace face = faces.get(i/3);
            elements[i]=face.mIndices().get(0);
            elements[i+1]=face.mIndices().get(1);
            elements[i+2]=face.mIndices().get(2);

        }
        /*for (BJEVertex ver: BJEVerteciesList
             ) {
            System.out.println(ver.toString());
        }
        for (int i = 0; i < BJEVerteciesList.size()*BJEVertex.SIZE; i+=BJEVertex.SIZE) {
            System.out.println(vertexes[i]+ " "+vertexes[i+1]+ " "+vertexes[i+2]);


        }*/
        setup();
    }
    public void Draw(){

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, FaceBuffLen,GL_UNSIGNED_INT,0);

    }
    public void Clear(){


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glDeleteBuffers(vboID);


    }



}
