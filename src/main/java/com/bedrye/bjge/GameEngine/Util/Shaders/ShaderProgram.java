package com.bedrye.bjge.GameEngine.Util.Shaders;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
    private int programId;
    public ShaderProgram(Path path){


    }

    public abstract void Run();
    public abstract void Compile();
    public abstract void Clear();


    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
    public void UploadMatrix(Matrix4f matrix4f, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        matrix4f.get(floatBuffer);
        glUniformMatrix4fv(loc,false,floatBuffer);


    }
    public void uploadVec4f(Vector4f vec4f, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        glUniform4f(loc,vec4f.x,vec4f.y,vec4f.z,vec4f.w);

    }
    public void uploadVec3f(Vector3f vec3f, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        glUniform3f(loc,vec3f.x,vec3f.y,vec3f.z);

    }
    public void uploadVec2f(Vector2f vec2f, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        glUniform2f(loc,vec2f.x,vec2f.y);

    }
    public void uploadFloat(Float flt, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        glUniform1f(loc,flt);

    }
    public void uploadInt(int i, String name){
        int loc = glGetUniformLocation(getProgramId(),name);
        glUniform1i(loc,i);

    }


}
