package com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs;

import com.bedrye.Objects.Object3D;
import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Util.BJEObjFile;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class Cube extends Object3D {

    public Cube(){
        super();
        addScript(new BJEMeshRenderer(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Cube.obj","Cube.obj"),new BJETexture("Assets\\Egg.png","Egg.png")));


    }
}
