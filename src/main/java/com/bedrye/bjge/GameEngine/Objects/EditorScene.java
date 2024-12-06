package com.bedrye.bjge.GameEngine.Objects;

import com.almasb.fxgl.core.math.Vec3;

import static org.lwjgl.opengl.GL11.*;

public class EditorScene extends Scene{


    @Override
    public void initialize() {
        System.out.println("Editing");
    }

    @Override
    public void initialize(Vec3 position) {

    }

    @Override
    public void update() {
        glClearColor(0.3f,0.3f,0.3f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

    }
}
