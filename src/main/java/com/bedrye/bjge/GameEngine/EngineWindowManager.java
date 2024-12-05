package com.bedrye.bjge.GameEngine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class EngineWindowManager {
    private String projectName;
    private int height,width;
    private long windowAddress;

    private static EngineWindowManager engineWindowManager;
    private EngineWindowManager(){
        projectName = "Test";
        height = 1280;
        width=1920;
    }
    public static EngineWindowManager getInstance(){
        if(engineWindowManager==null){engineWindowManager = new EngineWindowManager();}
        return engineWindowManager;
    }
    public void run(){

        init();
        update();

        glfwFreeCallbacks(windowAddress);
        glfwDestroyWindow(windowAddress);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()){
            throw new RuntimeException("Unable to setup GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);


        windowAddress = glfwCreateWindow(width,height,projectName, NULL,NULL);
        if(windowAddress == NULL){
            throw new IllegalStateException("Unable to create a window");

        }
        glfwMakeContextCurrent(windowAddress);
        glfwSwapInterval(1);
        glfwShowWindow(windowAddress);
        GL.createCapabilities();

    }
    public void update(){
        while (!glfwWindowShouldClose(windowAddress)){

            glfwPollEvents();
            glClearColor(0.5f,0.5f,0.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(windowAddress);
        }


    }


}
