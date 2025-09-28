package com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.Object3D;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Util.BJEObjFile;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class Sphere extends Object3D {

    public Sphere(){
        super();
        addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Sphere.obj","Sphere.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));


    }
}
