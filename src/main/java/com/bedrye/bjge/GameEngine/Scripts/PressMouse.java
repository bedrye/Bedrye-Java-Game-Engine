package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class PressMouse extends MainBehaviour {
    public int move = 1;
    @Override
    public void update(){
        if (MouseListener.getInstance().isHold(0)) {
            getGameObject().setRotationY(getGameObject().getRotationY() + move);
            System.out.println(getGameObject().getRotationY() + " a ");

        }

    }
}
