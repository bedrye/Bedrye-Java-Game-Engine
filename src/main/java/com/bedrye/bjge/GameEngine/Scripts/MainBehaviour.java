package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)
public abstract class MainBehaviour {
    private Object3DAbstract gameObject;

    public void start(){};
    public void update(){};
    public void init(){};
    public void onTransformChange(){};

    public Object3DAbstract getGameObject() {
        return gameObject;
    }

    public void setGameObject(Object3DAbstract gameObject) {
        this.gameObject = gameObject;
    }

}
