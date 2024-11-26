package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;

public class GameScene extends Object3DAbstract {
    private Camera camera;

    public GameScene(){
        this.camera = null;
    }
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    @Override
    public void initialize() {
        System.out.println("GS");
        getChildList().forEach(Object3DAbstract::initialize);
    }
    
    @Override
    public void initialize(Vec3 position) {

    }
}
