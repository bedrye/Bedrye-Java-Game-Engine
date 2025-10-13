package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.Objects.Editor.UI.*;

import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class BJEEditorScene extends Scene {
    @JsonIgnore
    public BJEUIInspector inspector = new BJEUIInspector();
    @JsonIgnore
    public BJEUISceneHierarchy sceneHierarchy = new BJEUISceneHierarchy();
    @JsonIgnore
    public BJEEditorViewport bjeEditorViewport = new BJEEditorViewport();

    @JsonIgnore
    public BJEAssetHierarchy bjeAssetHierarchy;


    @Override
    public void initialize() {

        System.out.println("Editor");


        bjeAssetHierarchy= new BJEAssetHierarchy();
        Camera camera = new Camera();
        setCamera(camera);

        camera.addScript(new SimpleCameraControl());


        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();

        getChildList().forEach(Object3DAbstract::initialize);
        setInitialized(true);



    }

    @Override
    public void updateUILayer() {
        sceneHierarchy.update();
        inspector.update();
        bjeEditorViewport.update();

        bjeAssetHierarchy.update();


    }


    @Override
    public void update() {
        glClearColor(0.8f,0.8f,0.8f,1.0f);

        glClear(GL_COLOR_BUFFER_BIT);
        getShaderProgram().Run();

        glActiveTexture(GL_TEXTURE0);
        getCamera().update();
        getShaderProgram().uploadFloat(10f,"specularPower");
        getChildList().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
