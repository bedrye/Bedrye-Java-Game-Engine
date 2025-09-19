package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joml.Vector3f;

public class AmbientLight extends Light {



    public AmbientLight() {
        super();
        this.setName("AmbientLight");
        setColor(new Vector3f(0.3f,0.3f,0.3f));
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


    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }


    @Override
    public void initialize() {

        //EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createPointLightUniform(this);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().uploadVec3f(getColor(),"ambientLight");
    }
    public static class Attenuation {

        private float constant;
        private float exponent;
        private float linear;
        @JsonCreator
        public Attenuation(@JsonProperty("constant") float constant,@JsonProperty("linear") float linear, @JsonProperty("exponent")float exponent) {
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
