package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;

import java.util.ArrayList;

public class Object3DEmpty extends Object3DAbstract{

    private Object3DAbstract parent;

    public Object3DEmpty(Object3DAbstract parent){
        super();
        this.parent=parent;
    }
    public Object3DEmpty(){
        super();
    }
    public Object3DAbstract getParent() {
        return parent;
    }

    public void setParent(Object3DAbstract parent) {
        this.parent = parent;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(Vec3 position) {

    }
}
