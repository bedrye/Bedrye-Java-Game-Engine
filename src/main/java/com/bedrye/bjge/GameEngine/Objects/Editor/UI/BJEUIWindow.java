package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class BJEUIWindow {
    private Vector2f position = new Vector2f();
    private Vector2f size = new Vector2f();

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x,float y) {
        this.position.x=x;
        this.position.y=y;
    }

    public void setSize(float x,float y) {
        this.size.x=x;
        this.size.y=y;
    }
    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }
    public Vector4f getBounds(){

        return  new Vector4f(position.x,position.y,position.x+size.x,position.y+size.y);
    }

    public abstract void update();
}
