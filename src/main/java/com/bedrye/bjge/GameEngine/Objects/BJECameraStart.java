package com.bedrye.bjge.GameEngine.Objects;

import com.bedrye.bjge.GameEngine.EngineManager;

import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import org.joml.Vector3f;

public class BJECameraStart extends Object3DAbstract{
    @InspectorVisible
    private boolean isPerspectiveProjection = true;
    @InspectorVisible
    private float zNear = 1.0f;
    @InspectorVisible
    private float zFar = 1000.0f;
    @Override
    public void gameInitialize() {
        Camera camera = new Camera(getParent());
        EngineManager.getInstance().getActiveScene().setCamera(camera);
        camera.setLocalPos(getPosition());
        camera.setzFar(zFar);
        camera.setzNear(zNear);
        camera.setPerspectiveProjection(isPerspectiveProjection);
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
