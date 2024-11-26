package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;
import javafx.scene.paint.Material;
import javafx.scene.shape.Shape3D;

public class Object3D extends Object3DEmpty {
    private Shape3D mesh;
    private Material material;
    public Object3D(Shape3D mesh, Material material){
        super();
        this.mesh=mesh;
        setMaterial(material);
    }
    public void setMaterial(Material material){
        this.material=material;
        mesh.setMaterial(material);
    }
    public Material getMaterial(){
        return material;
    }

    public Shape3D getMesh() {
        return mesh;
    }

    @Override
    public void initialize(){
        System.out.println("B");
        getChildList().forEach(Object3DAbstract::initialize);
    }
}
