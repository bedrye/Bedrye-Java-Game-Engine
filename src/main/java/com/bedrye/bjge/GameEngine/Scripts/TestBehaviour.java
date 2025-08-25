package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;

public class TestBehaviour extends MainBehaviour{

    public String publicString ="22222222222";
    @InspectorVisible
    private float reflectance=3;
    private double  privateAndNotVisible = 11;
    @Override
    public void start(){
        System.out.println("I launched");


    }

    @Override
    public void update(){
        //System.out.println("I work");


    }

}
