package com.bedrye.bjge.GameEngine;

import java.io.IOException;

public class GameEngineMain {

    private boolean isInGameMode;

    public EngineFileSystem engineFileSystem;
    //private EngineEditorSystem engineEditorSystem;

    public GameEngineMain() {
        this.isInGameMode = false;
            this.engineFileSystem = new EngineFileSystem("O:\\Tests");
        //this.engineEditorSystem = engineEditorSystem;
    }
}
