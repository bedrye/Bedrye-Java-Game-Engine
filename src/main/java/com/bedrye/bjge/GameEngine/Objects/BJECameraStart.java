package com.bedrye.bjge.GameEngine.Objects;

import com.bedrye.bjge.GameEngine.EngineManager;

import org.joml.Vector3f;

public class BJECameraStart extends Object3DAbstract{
    @Override
    public void gameInitialize() {
        Camera camera = new Camera(getParent());
        EngineManager.getInstance().getActiveScene().setCamera(camera);
        camera.setLocalPos(getPosition());
        getScriptList().forEach(camera::addScript);



    }

    @Override
    public void initialize() {
        getChildList().forEach(Object3DAbstract::initialize);
    }

    @Override
    public void initialize(Vector3f position) {
        getChildList().forEach(Object3DAbstract::initialize);
    }

    @Override
    public void update() {
        getChildList().forEach(Object3DAbstract::update);
    }
}
