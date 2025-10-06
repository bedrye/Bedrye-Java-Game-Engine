package com.bedrye.bjge.GameEngine.Objects.Editor;

import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Scripts.Box3DCollider;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Scripts.TestBehaviour;


import java.util.HashMap;


public class BJEResourceManager {

    private HashMap<String ,MainBehaviour> scripts = new HashMap<>();

    public HashMap<String, MainBehaviour> getScripts() {
        return scripts;
    }

    public BJEResourceManager(){
        scripts.put(SimpleCameraControl.class.getName(),new SimpleCameraControl());
        scripts.put(BJEMeshRenderer.class.getName(),new BJEMeshRenderer());
        scripts.put(TestBehaviour.class.getName(),new TestBehaviour());
        scripts.put(Box3DCollider.class.getName(),new Box3DCollider());
    }

    public void addScript(MainBehaviour mainBehaviour){
        scripts.put(mainBehaviour.getClass().getName(),mainBehaviour);


    }


}
