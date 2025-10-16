package com.bedrye.bjge.GameEngine.Objects;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.glClearColor;

public class SkyColor extends Object3DAbstract {

    public Vector3f color = new Vector3f(0.2f,0.2f,0.2f);
    @Override
    public void preRender(){
        glClearColor(color.x,color.y,color.z,1.0f);
    }
}
