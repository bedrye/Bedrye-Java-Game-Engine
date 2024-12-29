package com.bedrye.bjge.GameEngine.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.Objects.Camera;
import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;

import java.util.ArrayList;

public abstract class Scene {
    private Camera camera;
    private boolean initialized = false;
    private ArrayList<Object3DAbstract> gameObjects= new ArrayList<>();

    private ShaderProgram shaderProgram;
    public Scene(){
        this.camera = null;
    }
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public abstract void update();


    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public abstract void initialize() ;


    public ArrayList<Object3DAbstract> getGameObjects() {
        return gameObjects;
    }

}
