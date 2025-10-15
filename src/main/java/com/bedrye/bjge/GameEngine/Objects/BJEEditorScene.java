package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.*;

import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.BJEGizmo;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joml.Matrix4f;


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
    BJEGizmo bjeGizmo = new BJEGizmo();
    BasicProgramShader gizmoshader;
    @JsonIgnore
    public BJEAssetHierarchy bjeAssetHierarchy;


    @Override
    public void initialize() {

        System.out.println("Editor");


        bjeAssetHierarchy= new BJEAssetHierarchy();
        Camera camera = new Camera();
        setCamera(camera);

        camera.addScript(new SimpleCameraControl());

        gizmoshader = (BasicProgramShader) EngineWindowManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\SimpleGizmoShader.glsl");
        setShaderProgram((BasicProgramShader) EngineWindowManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\SimpleBuiltInShader.glsl"));
        getShaderProgram().Compile();
        gizmoshader.Compile();
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

    @Override
    public void render(){
        glClearColor(0.8f,0.8f,0.8f,1.0f);

        glClear(GL_COLOR_BUFFER_BIT);
        getShaderProgram().Run();

        glActiveTexture(GL_TEXTURE0);
        getShaderProgram().uploadFloat(10f,"specularPower");
        getChildList().forEach(Object3DAbstract::render);

        getShaderProgram().Clear();
        renderGizmos();
    }
    @Override
    public void renderGizmos() {

        gizmoshader.Run();
        if(inspector.getObject3DAbstract()!=null) {
            float dist = getCamera().getLocalPos().distance(inspector.getObject3DAbstract().getLocalPos());
            bjeGizmo.draw(getCamera().getProjectionMatrix(), getCamera().getViewMatrix(), inspector.getObject3DAbstract().getTransformMatrix(), gizmoshader);
        }
            gizmoshader.Clear();
    }

}
