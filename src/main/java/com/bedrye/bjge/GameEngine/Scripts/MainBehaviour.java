package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
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

    public final boolean isActive() {
        return active;
    }

    public final void setActive(boolean active) {
        this.active = active;
    }

    @InspectorVisible
    private boolean active;


    public void init(){};
    public void update(){};
    public void render(){};
    public void onTransformChange(){};

    public Object3DAbstract getGameObject() {
        return gameObject;
    }

    public void setGameObject(Object3DAbstract gameObject) {
        this.gameObject = gameObject;
    }
    public void fixedUpdate(){}
    public void preRender(){}

}
