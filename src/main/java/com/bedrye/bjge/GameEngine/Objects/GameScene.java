package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.Objects.Editor.UI.*;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class GameScene extends Scene {
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

        System.out.println("Game");
        /*vertexs.add(new BJEVertex(new Vector3f(0.5f,0.5f,0.0f),new Vector4f(0.0f,0.0f,0.5f,1.0f),  new Vector2f(0.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,0.5f,0.0f),new Vector4f(1.0f,0.0f,0.0f,1.0f),  new Vector2f(1.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.0f,1.0f),  new Vector2f(1.0f,1.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(0.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.5f,1.0f),  new Vector2f(0.0f,1.0f),new Vector3f()));
       */

        bjeAssetHierarchy= new BJEAssetHierarchy();
        Camera camera = new Camera(null);
        setCamera(camera);

        camera.addScript(new SimpleCameraControl());


        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();

        getGameObjects().forEach(Object3DAbstract::initialize);
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
        getShaderProgram().uploadInt(0,"texttr");
        glActiveTexture(GL_TEXTURE0);
        getCamera().update();


        //getShaderProgram().UploadMatrix(getCamera().getProjectionMatrix(),"projectionMatrix");
        //getShaderProgram().UploadMatrix(getCamera().getViewMatrix(),"viewMatrix");
        getShaderProgram().uploadFloat(10f,"specularPower");
        getGameObjects().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
