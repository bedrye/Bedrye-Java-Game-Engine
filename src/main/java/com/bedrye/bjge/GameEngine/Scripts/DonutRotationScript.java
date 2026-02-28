package com.bedrye.bjge.GameEngine.Scripts;

public class DonutRotationScript extends MainBehaviour{
    @Override
    public void fixedUpdate() {
        getGameObject().setRotationZ( getGameObject().getRotationZ()+1);
    }
}
