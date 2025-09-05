package com.bedrye.bjge.GameEngine;

import com.bedrye.Objects.GameScene;
import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Objects.EditorScene;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Util.BJEFrameBuffer;
import com.bedrye.bjge.GameEngine.Util.TimeCounter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class EngineWindowManager {
    private String projectName;
    private int height,width;


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private long windowAddress;
    private BJEIMGUILayer bjeimguiLayer;
    private BJEFrameBuffer bjeFrameBuffer;

    public BJEFrameBuffer getBjeFrameBuffer() {
        return bjeFrameBuffer;
    }

    public long getWindowAddress() {
        return windowAddress;
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene activeScene) {
        this.activeScene = activeScene;
        activeScene.initialize();
    }

    private Scene activeScene;
    public static EngineWindowManager init;
    public static EngineWindowManager getInstance(){
        if(init==null) init= new EngineWindowManager();
        return init;

    }

    private EngineWindowManager(){
        projectName = "Test";
        height = 1080;
        width=1920;
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
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);


        windowAddress = glfwCreateWindow(width,height,projectName, NULL,NULL);
        if(windowAddress == NULL){
            throw new IllegalStateException("Unable to create a window");

        }

        glfwSetCursorEnterCallback(windowAddress, MouseListener.getInstance()::mouseEnterCallBack);
        glfwSetCursorPosCallback(windowAddress, MouseListener.getInstance()::mousePosCallback);
        glfwSetMouseButtonCallback(windowAddress, MouseListener.getInstance()::mouseClickCallBack);
        glfwSetScrollCallback(windowAddress, MouseListener.getInstance()::mouseScrollCallBack);



        glfwSetKeyCallback(windowAddress, KeyListener.getInstance()::keyPressCallback);

        glfwMakeContextCurrent(windowAddress);
        glfwSwapInterval(1);
        glfwShowWindow(windowAddress);


        GL.createCapabilities();
        bjeimguiLayer = new BJEIMGUILayer(windowAddress);
        bjeimguiLayer.init();
        bjeFrameBuffer = new BJEFrameBuffer(getWidth(),getHeight());
        glViewport(0,0,getWidth(),getHeight());
        glEnable(GL_DEPTH_TEST);
        glClear( GL_DEPTH_BUFFER_BIT);

        setActiveScene(new GameScene());
        glEnable(GL_CULL_FACE);
        //glPolygonMode(GL_FRONT, GL_LINE);

        //glCullFace(GL_FRONT);


    }
    public void update(){
        while (!glfwWindowShouldClose(windowAddress)){

            glfwPollEvents();

            bjeimguiLayer.newFrame();
            bjeimguiLayer.setupDockspace();
            activeScene.updateUILayer();
            bjeimguiLayer.endFrame();
            bjeFrameBuffer.bind();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            activeScene.update();
            bjeFrameBuffer.unbind();

            glfwSwapBuffers(windowAddress);


        }


    }
    public float getTargetAspectRatio() {
        return (float)width / height;
    }


}
