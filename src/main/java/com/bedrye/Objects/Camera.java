package com.bedrye.Objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Object3DAbstract {
    private Matrix4f projectionMatrix,viewMatrix;

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    public Matrix4f getViewMatrix() {

        return viewMatrix;
    }

    public Camera(Object3DAbstract parent) {
        super(parent);

        projectionMatrix=new Matrix4f();
        viewMatrix=new Matrix4f();
        changeProjection();
        compViewMatrix();

    }

    public void initialize(){


    }

    @Override
    public void initialize(Vector3f position) {


    }

    @Override
    public void update() {

    }

    private void changeProjection(){
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f,32.0f*40.0f,0.0f,32.0f*21.0f,0.0f,2000.0f);


    }
    private void compViewMatrix(){

        Vector3f camFront = new Vector3f(0.0f,0.0f,-1.0f);
        Vector3f camTop = new Vector3f(0.0f,1.0f,0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(getGlobalX(), getGlobalY(),20.0f)
                ,camFront.add(getGlobalX(), getGlobalY(),0.0f),camTop);
    }


}
