package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class TestBehaviour extends MainBehaviour{

    public String publicString ="22222222222";
    @InspectorVisible
    private float reflectance=3;
    private double  privateAndNotVisible = 11;
    public BJETexture texture;
    @Override
    public void start(){
        System.out.println("I launched");


    }

    @Override
    public void update(){
        //System.out.println("I work");


    }

}
