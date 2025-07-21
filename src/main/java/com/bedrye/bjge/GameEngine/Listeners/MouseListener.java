package com.bedrye.bjge.GameEngine.Listeners;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class MouseListener {
    private static MouseListener mouseListener;
    public static MouseListener getInstance(){
        if(mouseListener==null) mouseListener = new MouseListener();
        return mouseListener;
    }
    private double posX,posY;
    private double lastPosX, lastPosY;
    private double scrollX, scrollY;
    private boolean isInWindow;

    private boolean pressedMouseButtons[] = new boolean[3];


    private MouseListener(){
        posX=0;
        posY=0;
        lastPosX=0;
        lastPosY=0;
        scrollX=0;
    }
    public void mousePosCallback(long windowAddress,double x,double y){

            lastPosY = posY;
            lastPosX = posX;
        if(isInWindow) {
            posX = x;
            posY = y;
        }


    }
    public void mouseEnterCallBack(long windowAddress,boolean entered){
        isInWindow=entered;
    }
    public void mouseClickCallBack(long windowAddress,int button,int action,int mods){
        if(button<3) {
            if (action == GLFW_PRESS)
                pressedMouseButtons[button]=true;
            else if (action == GLFW_RELEASE)
                pressedMouseButtons[button] = false;
            }
        }




    public void mouseScrollCallBack(long windowAddress,double x,double y){
        scrollX=x;
        scrollY=y;
    }

    public void frameEnd(){
        lastPosY= posY;
        lastPosX=posX;
        scrollX=0;
        scrollY=0;



    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getChangeX() {
        return lastPosX-posX;
    }

    public double getChangeY() {
        return lastPosY-posX;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public boolean isHold(int button) {
        if(button<pressedMouseButtons.length&&button>=0)
        return pressedMouseButtons[button];
        else throw new IndexOutOfBoundsException("Mouse button id is out of bounds [0,"
                +pressedMouseButtons.length+"]");
    }
}
