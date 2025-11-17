package com.bedrye.bjge.GameEngine;

import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Objects.Scene;

public final class BJEGameLogic {
    private Scene activeScene;

    public void setActiveScene(Scene activeScene) {
        this.activeScene = activeScene;
        activeScene.initialize();
    }
    public Scene getActiveScene() {
        return activeScene;
    }

    public void update() {
        activeScene.update();
    }

    public void render() {
        activeScene.render();
    }

    public void preRender() {
        activeScene.preRender();
    }

    public void fixedUpdate() {
        activeScene.fixedUpdate();
    }
    public void renderGizmos() {
        activeScene.renderGizmos();
    }
}

