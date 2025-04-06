package com.bedrye.bjge.GameEngine;

import java.io.IOException;

public class GameEngineMain {

    private boolean isInGameMode;

    public EngineWindowManager engineWindowManager;
    private static GameEngineMain init;
    public static GameEngineMain getInstance(){
        if(init==null) init= new GameEngineMain();
        return init;

    }

    //private EngineEditorSystem engineEditorSystem;

    private GameEngineMain() {
        this.isInGameMode = false;

        engineWindowManager = new EngineWindowManager();
        engineWindowManager.run();
        //this.engineEditorSystem = engineEditorSystem;
    }
}
