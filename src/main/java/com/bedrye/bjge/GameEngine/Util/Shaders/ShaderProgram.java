package com.bedrye.bjge.GameEngine.Util.Shaders;


import com.bedrye.bjge.GameEngine.Objects.AmbientLight;
import com.bedrye.bjge.GameEngine.Objects.DirectionalLight;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
    private int programId;
    private Map<String,Integer> uploadedStuff;
    public ShaderProgram(Path path){


    }

    public abstract void Run();
    public abstract void Compile();
    public abstract void Clear();

    private int getKey(String name){

        int loc =0;
        if(!uploadedStuff.containsKey(name)) {
            loc = glGetUniformLocation(getProgramId(), name);
            uploadedStuff.put(name,loc);
        }
        else
            loc = uploadedStuff.get(name);
        return  loc;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
        uploadedStuff= new HashMap<>();
    }
    public void UploadMatrix(Matrix4f matrix4f, String name){
        int loc = getKey(name);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        matrix4f.get(floatBuffer);
        glUniformMatrix4fv(loc,false,floatBuffer);


    }
    public void uploadVec4f(Vector4f vec4f, String name){
        int loc = getKey(name);
        glUniform4f(loc,vec4f.x,vec4f.y,vec4f.z,vec4f.w);

    }
    public void uploadVec3f(Vector3f vec3f, String name){
        int loc = getKey(name);
        glUniform3f(loc,vec3f.x,vec3f.y,vec3f.z);

    }
    public void uploadVec2f(Vector2f vec2f, String name){
        int loc = getKey(name);
        glUniform2f(loc,vec2f.x,vec2f.y);

    }
    public void uploadFloat(Float flt, String name){
        int loc = getKey(name);
        glUniform1f(loc,flt);

    }
    public void uploadInt(int i, String name){
        int loc = getKey(name);
        glUniform1i(loc,i);

    }
    public void createDirectionalLightUniform(DirectionalLight directionalLight) {
        uploadVec3f(directionalLight.getColor(), "directionalLight.colour" );
        uploadVec3f( directionalLight.getDirection(),  "directionalLight.position");
        uploadFloat( directionalLight.getIntensity(), "directionalLight.intensity" );


    }


    public void createPointLightUniform(AmbientLight ambientLight) {
        uploadVec3f(ambientLight.getColor(), ambientLight.getName()+ ".colour" );
        uploadVec3f( ambientLight.getPosition(), ambientLight.getName() + ".position");
        uploadFloat( ambientLight.getIntensity(), ambientLight.getName()+ ".intensity" );
        AmbientLight.Attenuation att = ambientLight.getAttenuation();
        uploadFloat( att.getConstant() , ambientLight.getName() + ".att.constant");
        uploadFloat(att.getLinear(), ambientLight.getName() + ".att.linear");
        uploadFloat(att.getExponent(), ambientLight.getName() + ".att.exponent");

    }
    public void createMaterialUniform(BJEMaterial texture) {
        uploadVec4f( texture.getAmbient(),"material.ambient" );
        uploadVec4f( texture.getDiffuse(),"material.diffuse");
        uploadInt(texture.hasTexture()? 1 : 0, "material.hasTexture" );
        uploadVec4f( texture.getSpecular(), "material.specular" );
        uploadFloat( texture.getReflectance() ,"material.reflectance");


    }

}
