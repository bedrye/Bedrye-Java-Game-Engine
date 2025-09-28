package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
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
        changeProjection();
        compViewMatrix();
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().uploadVec3f(getPosition(),"camera_pos");
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getProjectionMatrix(),"projectionMatrix");
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getViewMatrix(),"viewMatrix");
        for (MainBehaviour beh: getScriptList()
             ) {
            beh.update();
        }
    }

    private void changeProjection(){
        projectionMatrix.identity();
        //projectionMatrix.ortho(-400.0f,400f,-210.0f,210.0f,0.0f,1000.0f);
        //System.out.println((float)EngineWindowManager.getInstance().getWidth()/EngineWindowManager.getInstance().getHeight());
        projectionMatrix.setPerspective((float) Math.toRadians(60.0f), (float)EngineWindowManager.getInstance().getWidth()/EngineWindowManager.getInstance().getHeight(),1f,1000.0f);



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
