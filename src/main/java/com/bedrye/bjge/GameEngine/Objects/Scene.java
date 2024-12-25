package com.bedrye.bjge.GameEngine.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.Objects.Camera;
import com.bedrye.Objects.Object3DAbstract;

public abstract class Scene extends Object3DAbstract {
    private Camera camera;
    private boolean initialized = false;


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
}
