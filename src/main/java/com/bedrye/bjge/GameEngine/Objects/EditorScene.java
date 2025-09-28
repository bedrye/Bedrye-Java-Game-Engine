package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.GameEngineMain;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Scripts.PressMouse;

import static org.lwjgl.opengl.GL11.*;

public final class EditorScene extends Scene{

    private GameScene gameScene = new GameScene();


    @Override
    public void initialize() {
        System.out.println("Editing");

    }

    @Override
    public void updateUILayer() {

    }


    //TODO
    @Override
    public void update() {
        glClearColor(0.3f,0.3f,0.3f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        
    }
}
