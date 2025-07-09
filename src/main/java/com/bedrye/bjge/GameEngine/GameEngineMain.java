package com.bedrye.bjge.GameEngine;

import java.io.IOException;

public class GameEngineMain {

    private boolean isInGameMode;


    private static GameEngineMain init;
    public static GameEngineMain getInstance(){
        if(init==null) init= new GameEngineMain();
        return init;

    }

    //private EngineEditorSystem engineEditorSystem;

    private GameEngineMain() {
        this.isInGameMode = false;

        EngineWindowManager.getInstance().run();
        //this.engineEditorSystem = engineEditorSystem;
    }
}
