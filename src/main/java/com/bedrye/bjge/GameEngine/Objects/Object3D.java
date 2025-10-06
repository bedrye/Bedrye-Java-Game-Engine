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
    public void initialize(){
        getChildList().forEach(Object3DAbstract::initialize);
        for (MainBehaviour mainBeh:getScriptList())
            if(mainBeh.getClass().isAnnotationPresent(EditorBehaviour.class))
                getScriptList().forEach(MainBehaviour::start);
    }

    @Override
    public void initialize(Vector3f position) {

        getChildList().forEach(Object3DAbstract::initialize);
        for (MainBehaviour mainBeh:getScriptList())
            if(mainBeh.getClass().isAnnotationPresent(EditorBehaviour.class))
                getScriptList().forEach(MainBehaviour::start);
    }

    @Override
    public void update() {
        for (MainBehaviour mainBeh:getScriptList()) {
            if(mainBeh.getClass().isAnnotationPresent(EditorBehaviour.class))
                mainBeh.update();

        }
        getChildList().forEach(Object3DAbstract::update);

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
