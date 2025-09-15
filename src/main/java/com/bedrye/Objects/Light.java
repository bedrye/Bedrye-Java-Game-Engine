package com.bedrye.Objects;

import org.joml.Vector3f;

public abstract class Light extends Object3DAbstract{

    public Vector3f color;

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }


}
