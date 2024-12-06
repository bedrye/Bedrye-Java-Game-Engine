package com.bedrye.bjge.GameEngine.Listeners;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class KeyListener {
    private static KeyListener keyListener;
    private boolean pressedKeys[] = new boolean[360];
    public static KeyListener getInstance(){
        if(keyListener==null) keyListener = new KeyListener();
        return keyListener;
    }
    private KeyListener(){

    }
    public void keyPressCallback(long windowAddress , int key,int code,int action,int mods){
        if (key<pressedKeys.length) {
            if (action == GLFW_PRESS)
                pressedKeys[key]=true;
            else if (action == GLFW_RELEASE)
                pressedKeys[key] = false;
        }

    }
    public boolean isKeyPressed( int key){
        if (key<pressedKeys.length) {
            return pressedKeys[key];
        } throw new IndexOutOfBoundsException("The Key id is out of bounds [0,"+pressedKeys.length+"]");
    }

}
