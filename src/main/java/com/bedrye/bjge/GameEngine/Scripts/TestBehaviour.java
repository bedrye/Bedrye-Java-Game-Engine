package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class TestBehaviour extends MainBehaviour{

    public String publicString ="test";
    @InspectorVisible
    private String visibleString="test2";
    private String invisibleString="test3";
    @Override
    public void init(){
        System.out.println("I launched");


    }

    @Override
    public void update(){
        //System.out.println("I work");


    }

}
