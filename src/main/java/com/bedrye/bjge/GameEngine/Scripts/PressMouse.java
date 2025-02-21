package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;

public class PressMouse extends MainBehaviour {
    @Override
    public void update(){
        if (MouseListener.getInstance().isHold(0)) {
            getGameObject().setRotationY(getGameObject().getRotationY() + 1);
            getGameObject().setLocalX(getGameObject().getGlobalX()+1);
        }

    }
}
