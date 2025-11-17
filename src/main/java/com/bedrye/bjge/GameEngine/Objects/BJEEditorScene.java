package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.*;

import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.BJEGizmo;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import com.fasterxml.jackson.annotation.JsonIgnore;


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
    BJEGizmo bjeGizmo = new BJEGizmo();
    @JsonIgnore
    BasicProgramShader gizmoshader;
    @JsonIgnore
    public BJEAssetHierarchy bjeAssetHierarchy;


    @Override
    public void initialize() {

        System.out.println("[BJE] Editor");


        bjeAssetHierarchy= new BJEAssetHierarchy();
        Camera camera = new Camera();
        setCamera(camera);

        camera.addScript(new SimpleCameraControl());

        gizmoshader = (BasicProgramShader) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\SimpleGizmoShader.glsl");
        setShaderProgram((BasicProgramShader) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\SimpleBuiltInShader.glsl"));
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



        tasks.forEach(ICommand::exec);
        getCamera().update();
        getChildList().forEach(Object3DAbstract::update);





    }

    @Override
    public void render(){

        getCamera().render();
        glActiveTexture(GL_TEXTURE0);
        getShaderProgram().uploadFloat(10f,"specularPower");
        getChildList().forEach(Object3DAbstract::render);

        getShaderProgram().Clear();
    }
    @Override
    public void renderGizmos() {
        glDisable(GL_DEPTH_TEST);
        gizmoshader.Run();
        if(inspector.getObject3DAbstract()!=null) {
            float dist = getCamera().getLocalPos().distance(inspector.getObject3DAbstract().getLocalPos());
            bjeGizmo.draw(getCamera().getProjectionMatrix(), getCamera().getViewMatrix(), inspector.getObject3DAbstract().getTransformMatrix(), gizmoshader);
        }
            gizmoshader.Clear();
        glEnable(GL_DEPTH_TEST);
    }

}
