package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.PressMouse;
import com.bedrye.bjge.GameEngine.Scripts.SImpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.*;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;


import java.nio.file.Paths;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class GameScene extends Scene {

    private ArrayList<BJEVertex> vertexs = new ArrayList<>();

    @Override
    public void initialize() {

        System.out.println("Game");
        /*vertexs.add(new BJEVertex(new Vector3f(0.5f,0.5f,0.0f),new Vector4f(0.0f,0.0f,0.5f,1.0f),  new Vector2f(0.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,0.5f,0.0f),new Vector4f(1.0f,0.0f,0.0f,1.0f),  new Vector2f(1.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.0f,1.0f),  new Vector2f(1.0f,1.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(0.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.5f,1.0f),  new Vector2f(0.0f,1.0f),new Vector3f()));
       */
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

        obj.addScript(new BJEMeshRenderer(new BJEMesh(new BJEResource("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png"))));
        AmbientLight ambientLight = new AmbientLight();
        Object3D obj2 = new Object3D();

        obj2.addScript(new BJEMeshRenderer(new BJEMesh(new BJEResource("O:\\GIt\\MultiplayerShooter\\Assets\\Cube.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png"))));
        obj2.addChild(obj);
        //obj2.addScript(new PressMouse());

        obj.setLocalPosX(10);
        obj2.setLocalPosZ(-50);
        Object3D obj4 = new Object3D();
        obj4.setLocalPosZ(-5);
        obj4.setLocalPosY(1);

        obj.addChild(obj4);
        obj.setScale(new Vector3f(0.5f,0.5f,0.5f));
        obj4.setScale(new Vector3f(0.2f,0.2f,0.2f));
        obj4.addScript(new BJEMeshRenderer(new BJEMesh(new BJEResource("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png"))));
        getGameObjects().add(obj2);
        //getGameObjects().add(obj3);
        getGameObjects().add(ambientLight);
        //ambientLight.addScript(new PressMouse());
        getGameObjects().add(directionalLight);
        BJEUIObject a = new BJEUIObject();
        //a.mainBehaviour = new PressMouse();
       a.setObject3DAbstract(obj2);
        //obj2.addScript(a.mainBehaviour);
        getGameObjects().add(a);
        getGameObjects().forEach(Object3DAbstract::initialize);
        setInitialized(true);


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
        getShaderProgram().uploadVec3f(new Vector3f(0.3f,0.3f,0.5f),"ambientLight");
        getShaderProgram().uploadFloat(10f,"specularPower");
        getGameObjects().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
