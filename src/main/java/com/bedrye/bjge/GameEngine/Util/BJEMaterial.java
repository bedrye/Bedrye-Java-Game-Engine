package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import org.joml.Vector4f;

@InspectorVisible
public class BJEMaterial{


    private Vector4f ambient;

    private Vector4f diffuse;

    private Vector4f specular;
    @InspectorVisible
    private BJETexture texture;
    @InspectorVisible
    private float reflectance;
    public Vector4f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    public BJETexture getTexture() {
        return texture;
    }

    public void setTexture(BJETexture texture) {
        this.texture = texture;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public BJEMaterial(Vector4f ambient, Vector4f diffuse, Vector4f specular, BJETexture texture, float reflectance) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.texture = texture;
        this.reflectance = reflectance;
    }
    public BJEMaterial(BJETexture texture) {

        ambient= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        specular = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        diffuse = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        reflectance =  0.5f;
        this.texture = texture;

    }
    public boolean hasTexture(){
        return texture!=null;
    }








}
