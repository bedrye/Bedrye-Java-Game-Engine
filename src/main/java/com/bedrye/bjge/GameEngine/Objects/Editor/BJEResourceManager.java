package com.bedrye.bjge.GameEngine.Objects.Editor;

import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Util.BJEResource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BJEResourceManager {

    private HashSet<MainBehaviour> scripts = new HashSet<>();

    public HashSet<MainBehaviour> getScripts() {
        return scripts;
    }
    public BJEResourceManager(){
        scripts.add(new SimpleCameraControl());
        scripts.add(new BJEMeshRenderer());
    }

    public void addScript(MainBehaviour mainBehaviour){
        scripts.add(mainBehaviour);


    }


}
