package com.bedrye.bjge.GameEngine;


import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Objects.BJEGameScene;
import com.bedrye.bjge.GameEngine.Objects.Editor.BJEResourceManager;

import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Util.*;
import com.bedrye.bjge.GameEngine.Util.Serialization.Vector3fDeserializer;
import com.bedrye.bjge.GameEngine.Util.Serialization.Vector3fSerializer;
import com.bedrye.bjge.GameEngine.Util.Serialization.Vector4fDeserializer;
import com.bedrye.bjge.GameEngine.Util.Serialization.Vector4fSerializer;


import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class EngineManager {

    private int height,width;
    private final double fixedTimeStamp = 5;

    public double getFixedTimeStamp() {
        return fixedTimeStamp;
    }

    private ObjectMapper mapper = new ObjectMapper();
    private SimpleModule module = new SimpleModule();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private long windowAddress;
    private BJEFrameBuffer bjeFrameBuffer;

    private BJEIMGUILayer bjeimguiLayer;
    private BJEResourceManager bjeResourceManager;
    private BJEGameLogic bjeGameLogic;
    private BJECommandManager bjeCommandManager;
    private BJEShortcutManager bjeShortcutManager;

    private boolean isInEditMode=true;
    public BJEShortcutManager getBjeShortcutManager() {
        return bjeShortcutManager;
    }
    public BJECommandManager getBjeCommandManager() {
        return bjeCommandManager;
    }

    public BJEFrameBuffer getBjeFrameBuffer() {
        return bjeFrameBuffer;
    }

    public long getWindowAddress() {
        return windowAddress;
    }

    public Scene getActiveScene() {
        return  bjeGameLogic.getActiveScene();
    }

    public void setActiveScene(Scene activeScene) {
        bjeGameLogic.setActiveScene(activeScene);
    }
    private String getProjectName(){

        if(hasProject())
            return project.getName();
        else return  "No Project Selected";
    }
    private BJESceneFile sceneFile;
    private BJEEditorScene editorScene;
    private BJEProject project;

    private static EngineManager init;
    public static EngineManager getInstance(){
        if(init==null) init= new EngineManager();
        return init;

    }
    public boolean hasProject(){
        return  project!=null;

    }
    private EngineManager(){
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

        module.addSerializer(Vector3f.class, new Vector3fSerializer());
        module.addDeserializer(Vector3f.class, new Vector3fDeserializer());
        module.addSerializer(Vector4f.class, new Vector4fSerializer());
        module.addDeserializer(Vector4f.class, new Vector4fDeserializer());
        mapper.registerModule(module);
        System.out.println("Jackson loaded: " + mapper);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

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


        windowAddress = glfwCreateWindow(width,height,getProjectName(), NULL,NULL);
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
        try {
            bjeResourceManager = new BJEResourceManager("Assets");
            bjeResourceManager.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bjeCommandManager = new BJECommandManager(10);
        bjeShortcutManager= new BJEShortcutManager();
        bjeGameLogic = new BJEGameLogic();
        editorScene = new BJEEditorScene();

        setActiveScene(editorScene);
        glEnable(GL_CULL_FACE);

        //glPolygonMode(GL_FRONT, GL_LINE);

        //glCullFace(GL_FRONT);


    }
    public void update(){
        double previous = TimeCounter.getRunTimeMiliSec();
        double lag = 0.0F;

        while (!glfwWindowShouldClose(windowAddress)){

            glfwPollEvents();
            double current = TimeCounter.getRunTimeMiliSec();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;
            bjeimguiLayer.newFrame();
            bjeimguiLayer.setupDockspace();
            bjeimguiLayer.updateUILayer();
            bjeimguiLayer.endFrame();
            bjeFrameBuffer.bind();
            KeyListener.getInstance().goThrough();
            while (lag >= fixedTimeStamp) {
                bjeGameLogic.fixedUpdate();
                lag -= fixedTimeStamp;
            }
            bjeGameLogic.update();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            bjeGameLogic.preRender();
            bjeGameLogic.render();
            bjeGameLogic.renderGizmos();
            bjeFrameBuffer.unbind();
            bjeResourceManager.update();
            glfwSwapBuffers(windowAddress);


        }


    }
    public float getTargetAspectRatio() {
        return (float)width / height;
    }
    public boolean isInSavedScene(){
        return         sceneFile!=null;
    }

    public void saveScene(){
        if(isInSavedScene()) {
            File file = new File(sceneFile.getPath());
            try {
                mapper.writeValue(file, editorScene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void loadSceneFromFile(){
        FileDialog dialog = getFileDialog();

        String dir = dialog.getDirectory();
        String file = dialog.getFile();


        if (file != null) {
            File selected = new File(dir, file);
            System.out.println("Selected: " + selected.getAbsolutePath());
            loadScene(selected);
        }


    }
    public void loadScene(BJESceneFile file){

        try {

            setEditorScene((BJEEditorScene) mapper.readValue(new File(file.getPath()), Scene.class));
            setActiveScene(editorScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadScene(File file){

        try {

            setEditorScene((BJEEditorScene) mapper.readValue(file, Scene.class));
            setActiveScene(editorScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BJEResourceManager getBjeResourceManager() {
        return bjeResourceManager;
    }

    private FileDialog getFileDialog() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open",FileDialog.LOAD);
        dialog.setVisible(true);
        dialog.setMultipleMode(false);
        dialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".bjes");
            }
        });
        return dialog;
    }
    public void saveSceneAs(){
        FileDialog dialog = new FileDialog((Frame) null, "Save Scene", FileDialog.SAVE);
        dialog.setFile("scene.bjes");
        dialog.setVisible(true);

        if (dialog.getFile() != null) {
            File file = new File(dialog.getDirectory(), dialog.getFile());
            try (FileWriter writer = new FileWriter(file)) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(writer, editorScene);
                System.out.println("Scene saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Save canceled.");
        }


    }
    private void setWorkSpace(String path,String projectName){
        bjeResourceManager.stop();
        try {
            project = new BJEProject(path,projectName);
            GLFW.glfwSetWindowTitle(getWindowAddress(), getProjectName());
            bjeResourceManager = new BJEResourceManager(path+"/Assets");
            bjeResourceManager.init();
            bjeCommandManager.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void loadWorkSpace() {
        FileDialog dialog = new FileDialog((Frame) null, "Select file to Open",FileDialog.LOAD);
        dialog.setVisible(true);
        dialog.setMultipleMode(false);
        dialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".bjep");
            }
        });

        String dir = dialog.getDirectory();
        String file = dialog.getFile();

        if (file != null) {
            System.out.println("[BJE] Selected workspace: " + dir+file);
            setWorkSpace(dir,file);
        }
    }
    public void createWorkSpace() {
        FileDialog dialog = new FileDialog((Frame) null, "Select where you want to create a new project", FileDialog.SAVE);

        dialog.setFile("New Project");

        dialog.setVisible(true);
        dialog.setMultipleMode(false);

        if (dialog.getFile() != null) {
            File newDir = new File(dialog.getDirectory()+ dialog.getFile()+"/Assets/scripts");
            File file = new File(dialog.getDirectory()+ dialog.getFile(),dialog.getFile()+".bjep");

            if (!newDir.exists()) {
                try {
                    Files.createDirectories(newDir.toPath());
                    file.createNewFile();
                    System.out.println("[BJE] Created workspace: " + dialog.getDirectory()+ dialog.getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            setWorkSpace(dialog.getDirectory()+ dialog.getFile(),dialog.getFile());
        }
    }
    public boolean isInEditMode() {
        return isInEditMode;
    }

    public void InEngineRun(){
        isInEditMode=false;
        Scene scene = new BJEGameScene(editorScene);

        setActiveScene(scene);

    }
    public void InEngineStop(){
        isInEditMode=true;
        setActiveScene(editorScene);
    }
    public void setEditorScene(BJEEditorScene bjeEditorScene){
        bjeCommandManager.clear();
        this.editorScene = bjeEditorScene;

    }

    public BJEEditorScene getEditorScene() {
        return editorScene;
    }
}

