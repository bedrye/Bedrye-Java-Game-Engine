package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.bedrye.bjge.GameEngine.Scripts.TestBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GameScene extends Scene {
    BJEMesh bjeMesh;

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
        getGameObjects().add(new Object3D(new BJEMesh(vertexes,elements),new BJETexture("Assets\\logog2.png")));
        getGameObjects().forEach(x->x.initialize());

    }



    @Override
    public void update() {
        glClearColor(0.8f,0.8f,0.8f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        getShaderProgram().Run();
        getShaderProgram().uploadInt(0,"texttr");
        glActiveTexture(GL_TEXTURE0);
        getGameObjects().forEach(x->x.update());
        getShaderProgram().UploadMatrix(getCamera().getProjectionMatrix(),"projectionMatrix");
        getShaderProgram().UploadMatrix(getCamera().getViewMatrix(),"viewMatrix");


        getShaderProgram().Clear();



    }

}
