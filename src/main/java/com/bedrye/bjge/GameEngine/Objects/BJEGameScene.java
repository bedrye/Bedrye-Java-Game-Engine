package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEEditorViewport;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import com.bedrye.bjge.GameEngine.Util.Shaders.BasicProgramShader;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public final class BJEGameScene extends Scene{

    public BJEEditorViewport bjeEditorViewport = new BJEEditorViewport();
    public BJEGameScene(BJEEditorScene scene){
        setGameObjects(scene.getChildList());
    }
    @Override
    public void initialize() {
        System.out.println("[BJE] Game");



        setShaderProgram((BasicProgramShader) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\SimpleBuiltInShader.glsl"));
        getShaderProgram().Compile();
        if(getCamera()==null) setCamera(new Camera(null));
        getChildList().forEach(Object3DAbstract::gameInitialize);
    }

    @Override
    public void updateUILayer() {
        bjeEditorViewport.update();
    }


    @Override
    public void update() {



        tasks.forEach(ICommand::exec);
        getCamera().gameUpdate();
        getChildList().forEach(Object3DAbstract::gameUpdate);





    }

    @Override
    public void render(){

        getCamera().render();
        glActiveTexture(GL_TEXTURE0);
        getShaderProgram().uploadFloat(10f,"specularPower");
        getChildList().forEach(Object3DAbstract::render);

        getShaderProgram().Clear();
        renderGizmos();
    }
    @Override
    public void renderGizmos() {}

}
