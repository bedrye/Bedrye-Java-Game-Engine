package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;

import static org.lwjgl.glfw.GLFW.*;

public class SImpleCameraControl extends MainBehaviour{

    @Override
    public void update() {
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_W)) {
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() - 1);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_S)) {
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() + 1);
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_A)) {
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() - 1);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_D)) {
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() + 1);
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_Z)) {
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() - 1);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_X)) {
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() + 1);
        }
        if (MouseListener.getInstance().isHold(1)) {
            //getGameObject().setRotationX(getGameObject().getRotationX() + (float) MouseListener.getInstance().getChangeY() / 10f);
            getGameObject().setRotationY(getGameObject().getRotationY() + (float) MouseListener.getInstance().getChangeX() / 10f);
        }
        //System.out.println(MouseListener.getInstance().getChangeX() );
    }


}
