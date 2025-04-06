package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.PressMouse;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;


import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class GameScene extends Scene {

    private float[] vertexes ={
            100.5f,0.5f,0.0f,   1.0f,0.0f,0.0f,1.0f, 1.0f,0.0f,
            0.5f,100.5f,0.0f,   0.0f,0.5f,0.5f,1.0f, 0.0f,1.0f,
            100.5f,100.5f,0.0f,   0.0f,0.5f,0.0f,1.0f, 1.0f,1.0f,
            0.5f,0.5f,0.0f,   0.0f,0.0f,0.5f,1.0f,  0.0f,0.0f

    };

    private int[] elements = {
        2,1,0, 0,1,3
    };


    @Override
    public void initialize() {
        setCamera(new Camera(null));
        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();
        //getGameObjects().add(new Object3D(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog2.png")));
        Object3D obj = new Object3D();
        obj.addScript(new BJEMeshRenderer(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog.png")));
        Object3D obj2 = new Object3D();
        obj2.addScript(new BJEMeshRenderer(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog2.png")));
        obj2.setLocalPosY(120f);
        obj2.setLocalPosX(120f);
        obj.addScript(new PressMouse());
        obj.addChild(obj2);
        getGameObjects().add(obj);
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

        getGameObjects().forEach(Object3DAbstract::update);

        getShaderProgram().Clear();



    }

}
