package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Util.Annotation.EditorBehaviour;

import static org.lwjgl.glfw.GLFW.*;
@EditorBehaviour
public class SimpleCameraControl extends MainBehaviour{

    @Override
    public void update() {
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_W)) {
            float l  =0.1f* (float)Math.cos(Math.toRadians(getGameObject().getRotationX()));
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() - 0.1f* (float)Math.sin(Math.toRadians(getGameObject().getRotationX())));
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() - l * (float)Math.cos(Math.toRadians(getGameObject().getRotationY())));
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() +  l * (float)Math.sin(Math.toRadians(getGameObject().getRotationY())));
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_S)) {
            float l  =0.1f* (float)Math.cos(Math.toRadians(getGameObject().getRotationX()));
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() +0.1f* (float)Math.sin(Math.toRadians(getGameObject().getRotationX())));
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() + l * (float)Math.cos(Math.toRadians(getGameObject().getRotationY())));
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() - l * (float)Math.sin(Math.toRadians(getGameObject().getRotationY())));
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_A)) {
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() + 0.1f* (float)Math.cos(Math.toRadians(getGameObject().getRotationY()+90)));
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() -  0.1f* (float)Math.sin(Math.toRadians(getGameObject().getRotationY()+90)));
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_D)) {
            getGameObject().setLocalPosZ(getGameObject().getLocalPosZ() - 0.1f* (float)Math.cos(Math.toRadians(getGameObject().getRotationY()+90)));
            getGameObject().setLocalPosX(getGameObject().getLocalPosX() +  0.1f* (float)Math.sin(Math.toRadians(getGameObject().getRotationY()+90)));
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_Z)) {
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() - 0.1f);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_X)) {
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() + 0.1f);
        }
        if (MouseListener.getInstance().isHold(1)) {
            getGameObject().setRotationX(getGameObject().getRotationX() + (float) MouseListener.getInstance().getChangeY() / 10f);
            getGameObject().setRotationY(getGameObject().getRotationY() + (float) MouseListener.getInstance().getChangeX() / 10f);
        }
        //System.out.println(MouseListener.getInstance().getChangeX() );
    }


}
