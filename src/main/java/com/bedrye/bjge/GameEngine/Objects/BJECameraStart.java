package com.bedrye.bjge.GameEngine.Objects;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.GameEngineMain;
import org.joml.Vector3f;

public class BJECameraStart extends Object3DAbstract{
    @Override
    public void initialize() {
        Camera camera = new Camera();
        EngineWindowManager.getInstance().getActiveScene().setCamera(camera);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {

    }
}
