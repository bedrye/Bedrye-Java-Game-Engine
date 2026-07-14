package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;

public class Object3D extends Object3DAbstract {
    public Object3D(){
        super();
    }
    public int a=1;
    public int b=1;
    @Override
    public void gameUpdate(){
        getScriptList().forEach(MainBehaviour::update);
        getChildList().forEach(Object3DAbstract::gameUpdate);
    }
    @Override
    public void gameInitialize(){
        getScriptList().forEach(MainBehaviour::init);
        getChildList().forEach(Object3DAbstract::gameInitialize);
    }

}
