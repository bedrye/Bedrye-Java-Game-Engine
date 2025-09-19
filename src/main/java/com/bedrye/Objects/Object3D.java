package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.GameEngineMain;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

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
