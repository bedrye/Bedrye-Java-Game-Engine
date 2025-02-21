package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.Objects.Object3DAbstract;

public abstract class MainBehaviour {
    private Object3DAbstract gameObject;

    public void start(){};
    public void update(){};
    public void init(){};

    public Object3DAbstract getGameObject() {
        return gameObject;
    }

    public void setGameObject(Object3DAbstract gameObject) {
        this.gameObject = gameObject;
    }
}
