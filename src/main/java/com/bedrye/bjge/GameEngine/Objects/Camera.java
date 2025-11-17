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
    private boolean isPerspectiveProjection = true;
    private float zNear = 1.0f;
    private float zFar = 1000.0f;
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
        if(isPerspectiveProjection)
            projectionMatrix.setPerspective((float) Math.toRadians(60.0f), (float) EngineManager.getInstance().getWidth()/ EngineManager.getInstance().getHeight(),zNear,zFar);
        else
            projectionMatrix.ortho(-8, 8, -5, 5, zNear, zFar);


    }

    private void compViewMatrix(){
        Vector3f cameraPos = getPosition();
        Vector3f rotation = getRotation();

        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0, 0, 1));

        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    }


    public boolean isPerspectiveProjection() {
        return isPerspectiveProjection;
    }

    public void setPerspectiveProjection(boolean perspectiveProjection) {
        isPerspectiveProjection = perspectiveProjection;
    }
    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
    }

}
