package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEEditorViewport;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;

import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public final class BJEGameScene extends Scene{

    public BJEEditorViewport bjeEditorViewport = new BJEEditorViewport();
    public BJEGameScene(BJEEditorScene scene){
        setGameObjects(scene.getChildList());
    }
    @Override
    public void initialize() {
        System.out.println("Game");



        setShaderProgram(new BasicProgramShader(Paths.get("Assets\\Shaders\\SimpleBuiltInShader.glsl")));
        getShaderProgram().Compile();

        getChildList().forEach(Object3DAbstract::gameInitialize);
        if(getCamera()==null) setCamera(new Camera(null));
    }

    @Override
    public void updateUILayer() {
        bjeEditorViewport.update();
    }


    //TODO
    @Override
    public void update() {
        glClearColor(0.8f,0.8f,0.8f,1.0f);

        glClear(GL_COLOR_BUFFER_BIT);
        getShaderProgram().Run();

        glActiveTexture(GL_TEXTURE0);
        getCamera().update();
        getShaderProgram().uploadFloat(10f,"specularPower");

        getChildList().forEach(Object3DAbstract::gameUpdate);

        getShaderProgram().Clear();

    }
}
