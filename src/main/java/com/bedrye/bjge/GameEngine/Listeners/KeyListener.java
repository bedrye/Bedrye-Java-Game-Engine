package com.bedrye.bjge.GameEngine.Listeners;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Util.Input.BJEKeyInput;
import com.bedrye.bjge.GameEngine.Util.Input.KeyInputType;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class KeyListener {
    private static KeyListener keyListener;
    private boolean pressedKeys[] = new boolean[360];

    private Queue<BJEKeyInput> inputsQueue = new ArrayDeque<>();

    private void addToQueue(KeyInputType keyInputType, int keyId){
        inputsQueue.add(new BJEKeyInput(keyInputType,keyId));

    }

    public void goThrough(){
        BJEKeyInput bjeKeyInput;
        while (!inputsQueue.isEmpty()) {

            bjeKeyInput = inputsQueue.remove();
            EngineWindowManager.getInstance().getBjeShortcutManager().onKeyPress(bjeKeyInput);
        }

    }
    public static KeyListener getInstance(){
        if(keyListener==null) keyListener = new KeyListener();
        return keyListener;
    }
    private KeyListener(){

    }
    public void keyPressCallback(long windowAddress , int key,int code,int action,int mods){
        if (key<pressedKeys.length) {
            if (action == GLFW_PRESS) {
                pressedKeys[key] = true;
                addToQueue(KeyInputType.Press, key);
            }
            else if (action == GLFW_RELEASE) {
                pressedKeys[key] = false;
                addToQueue(KeyInputType.Release, key);
            }
        }


    }
    public boolean isKeyPressed( int key){
        if (key<pressedKeys.length) {
            return pressedKeys[key];
        } throw new IndexOutOfBoundsException("The Key id is out of bounds [0,"+pressedKeys.length+"]");
    }

}
