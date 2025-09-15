package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEAssetHierarchy;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEEditorViewport;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIInspector;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUISceneHierarchy;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.PressMouse;
import com.bedrye.bjge.GameEngine.Scripts.SImpleCameraControl;
import com.bedrye.bjge.GameEngine.Scripts.TestBehaviour;
import com.bedrye.bjge.GameEngine.Util.*;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import org.joml.Vector3f;


import java.nio.file.Paths;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class GameScene extends Scene {

    public BJEUIInspector inspector = new BJEUIInspector();
    public BJEUISceneHierarchy sceneHierarchy = new BJEUISceneHierarchy();
    public BJEEditorViewport bjeEditorViewport = new BJEEditorViewport();
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

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setRotationY(90);
        camera.addScript(new SImpleCameraControl());


        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();
        //getGameObjects().add(new Object3D(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog2.png")));
        Object3D obj = new Object3D();
        //Object3D obj3 = new Object3D();

        obj.addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\sphere.obj","sphere.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));
        Object3D obj2 = new Object3D();

        obj2.addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Cube.obj","Cube.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));
        getGameObjects().add(obj);

        //obj2.addScript(new PressMouse());

        //obj.setLocalPosX(10);
        obj2.setLocalPosZ(-50);
        Object3D obj4 = new Object3D();
        obj4.setLocalPosZ(-5);
        obj4.setLocalPosY(1);

        obj.addChild(obj4);
        obj.setScale(new Vector3f(1f,1f,1f));
        obj4.setScale(new Vector3f(0.2f,0.2f,0.2f));
        obj4.addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj","Kulka.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));
        getGameObjects().add(obj2);
        //getGameObjects().add(obj3);
        //ambientLight.addScript(new PressMouse());
        getGameObjects().add(directionalLight);
        Object3D obj5 = new Object3D();
        //obj5.addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj","Kulka.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));
        obj.addScript(new PressMouse());
        //a.mainBehaviour = new PressMouse();
        getGameObjects().add(obj5);
        //obj2.addScript(a.mainBehaviour);
        getGameObjects().add(new AmbientLight());
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
        //getShaderProgram().uploadVec3f(new Vector3f(1f,1f,1f),"ambientLight");
        getShaderProgram().uploadFloat(10f,"specularPower");
        getGameObjects().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
