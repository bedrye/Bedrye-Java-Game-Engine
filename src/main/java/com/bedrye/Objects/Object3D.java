package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import javafx.scene.paint.Material;
import javafx.scene.shape.Shape3D;
import org.joml.Vector3f;

public class Object3D extends Object3DAbstract {
    private BJEMesh mesh;
    private BJETexture material;
    public Object3D(BJEMesh mesh, BJETexture material){
        super();
        this.mesh = mesh;
        this.material =material;
    }
    public void setMaterial(BJETexture material){
        this.material=material;

    }
    public BJETexture getMaterial(){
        return material;
    }

    public BJEMesh getMesh() {
        return mesh;
    }

    @Override
    public void initialize(){
        getChildList().forEach(Object3DAbstract::initialize);
        getScriptList().forEach(MainBehaviour::start);
    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {
        material.Bind();
        mesh.Draw();
        getChildList().forEach(Object3DAbstract::update);
        getScriptList().forEach(MainBehaviour::update);
        mesh.Clear();
        material.Unbind();
    }


}
