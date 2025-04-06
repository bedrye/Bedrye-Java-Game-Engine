package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Listeners.MouseListener;

public class PressMouse extends MainBehaviour {
    @Override
    public void update(){
        if (MouseListener.getInstance().isHold(0)) {
            getGameObject().setRotationY(getGameObject().getRotationY() + 1);
            getGameObject().setLocalPosX(getGameObject().getLocalPosX()+1);
            getGameObject().getChild(0).setRotationZ( getGameObject().getChild(0).getRotationZ() -1);
            System.out.print(getGameObject().getChild(0).getLocalPosX());
        }

    }
}
