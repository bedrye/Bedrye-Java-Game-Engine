package com.bedrye.bjge.GameEngine;

import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import com.bedrye.bjge.GameEngine.Util.BJETexture;

public class BJEMeshRenderer extends MainBehaviour {

    @InspectorVisible
    private BJEMesh mesh;
    @InspectorVisible
    private BJEMaterial material;
    public BJEMeshRenderer(BJEMesh mesh, BJEMaterial material){
        super();
        this.mesh = mesh;
        this.material =material;
        material.getTexture().initialize();
        mesh.setup();
    }

    @Override
    public void update() {


        material.getTexture().Bind();
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().createMaterialUniform(material);
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getGameObject().getTransformMatrix(),"modelMatrix");
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
        mesh.setup();
    }

    @Override
    public void onTransformChange() {
        mesh.setVertexesPosition(getGameObject().getModelViewMatrix());
    }

}
