package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;

import org.joml.Vector3f;

public class Object3D extends Object3DAbstract {
    public Object3D(){
        super();
    }

    @Override
    public void initialize(){
        getChildList().forEach(Object3DAbstract::initialize);
        getScriptList().forEach(MainBehaviour::start);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {
        //getBjeTransform().getWorldMatrix();
        getScriptList().forEach(MainBehaviour::update);
        getChildList().forEach(Object3DAbstract::update);

    }


}
