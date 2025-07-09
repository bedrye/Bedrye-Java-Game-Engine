package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.PressMouse;
import com.bedrye.bjge.GameEngine.Util.*;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class GameScene extends Scene {

    private ArrayList<BJEVertex> vertexs = new ArrayList<>();

    @Override
    public void initialize() {

        System.out.println("Game");
        vertexs.add(new BJEVertex(new Vector3f(0.5f,0.5f,0.0f),new Vector4f(0.0f,0.0f,0.5f,1.0f),  new Vector2f(0.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,0.5f,0.0f),new Vector4f(1.0f,0.0f,0.0f,1.0f),  new Vector2f(1.0f,0.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(100.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.0f,1.0f),  new Vector2f(1.0f,1.0f),new Vector3f()));
        vertexs.add(new BJEVertex(new Vector3f(0.5f,100.5f,0.0f),new Vector4f(0.0f,0.5f,0.5f,1.0f),  new Vector2f(0.0f,1.0f),new Vector3f()));
        setCamera(new Camera(null));
        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();
        //getGameObjects().add(new Object3D(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog2.png")));
        Object3D obj = new Object3D();
        obj.addScript(new BJEMeshRenderer(new BJEMesh(new BJEResource("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png"))));
        Light light = new Light();

        //obj2.addScript(new BJEMeshRenderer(new BJEMesh(new BJEResource("O:\\GIt\\MultiplayerShooter\\Assets\\Kulka.obj")),new BJETexture("O:\\GIt\\MultiplayerShooter\\Assets\\egg.png")));
        obj.setLocalPosY(1);
        //obj2.setScale(new Vector3f(10f,10f,10f));
        obj.setLocalPosX(2);
        obj.addScript(new PressMouse());
        //obj.addChild(obj2);
        getGameObjects().add(obj);
        getGameObjects().add(light);
        light.setLocalPosY(3);
        light.setLocalPosY(4);
        light.setLocalPosZ(-3);
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
        getShaderProgram().UploadMatrix(getCamera().getProjectionMatrix(),"projectionMatrix");
        getShaderProgram().UploadMatrix(getCamera().getViewMatrix(),"viewMatrix");
        getShaderProgram().uploadVec3f(new Vector3f(0.3f,0.3f,0.5f),"ambientLight");

        getGameObjects().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
