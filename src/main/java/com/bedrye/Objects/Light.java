package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.GameEngineMain;
import org.joml.Vector3f;

public class Light extends Object3DAbstract {

    private Vector3f color;

    public Light() {
        super();
        this.setName("pointLight");
        color =new Vector3f(1f,1f,1f);
        intensity = 1;
        attenuation =  new Attenuation(0, 0, 1);
    }
    private float intensity;

    private  Attenuation attenuation;

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }


    @Override
    public void initialize() {

        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createPointLightUniform(this);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {

    }
    public static class Attenuation {

        private float constant;
        private float exponent;
        private float linear;

        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        public float getConstant() {
            return constant;
        }

        public float getExponent() {
            return exponent;
        }

        public float getLinear() {
            return linear;
        }

        public void setConstant(float constant) {
            this.constant = constant;
        }

        public void setExponent(float exponent) {
            this.exponent = exponent;
        }

        public void setLinear(float linear) {
            this.linear = linear;
        }
    }

}
