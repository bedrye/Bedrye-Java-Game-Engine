package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Object3DAbstract {
    @JsonIgnore
    private Matrix4f projectionMatrix,viewMatrix;
    @JsonIgnore
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    @JsonIgnore
    public Matrix4f getViewMatrix() {

        return viewMatrix;
    }
    public Camera(){
        super();
        projectionMatrix=new Matrix4f();
        viewMatrix=new Matrix4f();
        changeProjection();
        compViewMatrix();

    }


    public Camera(IGameSpace parent) {
        super(parent);

        projectionMatrix=new Matrix4f();
        viewMatrix=new Matrix4f();
        changeProjection();
        compViewMatrix();

    }


    @Override
    public void update() {
        changeProjection();
        compViewMatrix();

        super.update();
    }

    @Override
    public void render() {
        EngineManager.getInstance().getActiveScene().getShaderProgram().uploadVec3f(getPosition(),"camera_pos");
        EngineManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getProjectionMatrix(),"projectionMatrix");
        EngineManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getViewMatrix(),"viewMatrix");
    }

    private void changeProjection(){
        projectionMatrix.identity();
        //projectionMatrix.setOrtho(0, EngineManager.getInstance().getWidth(),0, EngineManager.getInstance().getHeight(),1f,1000.0f);
        projectionMatrix.setPerspective((float) Math.toRadians(60.0f), (float) EngineManager.getInstance().getWidth()/ EngineManager.getInstance().getHeight(),1f,1000.0f);



    }

    private void compViewMatrix(){
        Vector3f cameraPos = getPosition();
        Vector3f rotation = getRotation();

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    }


}
