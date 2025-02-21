package com.bedrye.bjge.GameEngine;

import com.bedrye.Objects.BJEMesh;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glRotatef;

public class BJEMeshRenderer extends MainBehaviour {

    private BJEMesh mesh;
    private BJETexture material;
    public BJEMeshRenderer(BJEMesh mesh, BJETexture material){
        super();
        this.mesh = mesh;
        this.material =material;
    }

    @Override
    public void update() {
        mesh.setVertexesPosition(getGameObject().getTransformMatrix());
        material.Bind();
        mesh.Draw();
        mesh.Clear();
        material.Unbind();
    }

    private void alignVertexes(){



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

}
