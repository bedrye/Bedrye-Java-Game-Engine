package com.bedrye.bjge.GameEngine.Objects;


import static org.lwjgl.opengl.GL11.*;

public final class EditorScene extends Scene{


    @Override
    public void initialize() {
        System.out.println("Editing");
    }


    //TODO
    @Override
    public void update() {
        glClearColor(0.3f,0.3f,0.3f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
