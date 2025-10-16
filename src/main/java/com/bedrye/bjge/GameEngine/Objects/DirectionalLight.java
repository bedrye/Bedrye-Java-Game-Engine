package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;

import org.joml.Vector3f;



public class DirectionalLight extends Light {
    private Vector3f direction;

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public DirectionalLight() {
        super();
        direction = new Vector3f(0, 1, 1);
        setColor(new Vector3f(1f,1f,1f));
        intensity = 1;
    }
    private float intensity;
    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    @Override
    public void initialize() {
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createDirectionalLightUniform(this);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {
        getScriptList().forEach(MainBehaviour::update);

        direction = new Vector3f((float)Math.sin((float) Math.toRadians(-getRotationZ())),(float)Math.cos((float) Math.toRadians(-getRotationZ())),0);

    }
    @Override
    public void preRender(){
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createDirectionalLightUniform(this);
    }
}
