package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;

import com.bedrye.bjge.GameEngine.Util.Annotation.EditorBehaviour;
import org.joml.Vector3f;

public class Object3D extends Object3DAbstract {
    public Object3D(){
        super();
    }

    @Override
    public void gameUpdate(){
        getScriptList().forEach(MainBehaviour::update);
        getChildList().forEach(Object3DAbstract::gameUpdate);
    }
    @Override
    public void gameInitialize(){
        getScriptList().forEach(MainBehaviour::start);
        getChildList().forEach(Object3DAbstract::gameInitialize);
    }

}
