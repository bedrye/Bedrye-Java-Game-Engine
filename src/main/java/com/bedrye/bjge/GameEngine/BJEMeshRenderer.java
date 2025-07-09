package com.bedrye.bjge.GameEngine;

import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class BJEMeshRenderer extends MainBehaviour {

    private BJEMesh mesh;
    private BJEMaterial material;
    public BJEMeshRenderer(BJEMesh mesh, BJEMaterial material){
        super();
        this.mesh = mesh;
        this.material =material;
        material.getTexture().initialize();
    }

    @Override
    public void update() {
        mesh.setVertexesPosition(getGameObject().getTransformMatrix());

        material.getTexture().Bind();
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createMaterialUniform(material);
        mesh.Draw();
        mesh.Clear();
        material.getTexture().Unbind();
    }

    private void alignVertexes(){


    }
    public void setMaterial(BJEMaterial material){
        this.material=material;
        material.getTexture().initialize();

    }
    public BJEMaterial getMaterial(){
        return material;
    }

    public BJEMesh getMesh() {
        return mesh;
    }
    public void setMesh(BJEResource resource) {
        mesh = new BJEMesh(resource);
    }

}
