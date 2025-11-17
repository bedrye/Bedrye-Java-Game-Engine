package com.bedrye.bjge.GameEngine;


import com.bedrye.bjge.GameEngine.Util.*;
import com.bedrye.bjge.GameEngine.Util.Annotation.EditorBehaviour;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@EditorBehaviour
public class BJEMeshRenderer extends MainBehaviour {

    public BJEObjFile meshRes;

    public BJETexture texture;
    @JsonIgnore
    private BJEMesh mesh;
    @JsonIgnore
    private BJEMaterial material;

    public BJEMeshRenderer(){
        super();



    }
    public BJEMeshRenderer(BJEMesh mesh, BJEMaterial material){
        super();
        this.mesh = mesh;
        this.material =material;
        this.texture=material.getTexture();
        this.meshRes=mesh.getResource();
        //material.getTexture().initialize();
        mesh.setup();
    }
    @JsonCreator
    public BJEMeshRenderer(@JsonProperty("meshRes") BJEObjFile meshRes,@JsonProperty("texture") BJETexture texture){
        super();
        this.texture=texture;
        this.meshRes=meshRes;
        this.mesh = new BJEMesh(meshRes);
        this.material = new BJEMaterial(texture);
        //material.getTexture().initialize();
        mesh.setup();
    }
    @Override
    public void render() {
        if(texture==null||mesh==null)
            return;
        if(mesh.getResource()!=meshRes)
            setMesh(meshRes);
        texture.Bind();
        EngineManager.getInstance().getActiveScene().getShaderProgram().createMaterialUniform(material);
        EngineManager.getInstance().getActiveScene().getShaderProgram().UploadMatrix(getGameObject().getTransformMatrix(),"modelMatrix");
        mesh.Draw();
        mesh.Clear();
        texture.Unbind();
    }

    private void alignVertexes(){


    }
    public void setMaterial(BJEMaterial material){
        this.material=material;
        material.getTexture().initialize();

    }
    public void setTexture(BJETexture texture){
        this.texture=texture;
        material = new BJEMaterial(texture);
        material.getTexture().initialize();

    }
    public BJEMaterial getMaterial(){
        return material;
    }

    public BJEMesh getMesh() {
        return mesh;
    }
    public void setMesh(BJEObjFile resource) {
        mesh = new BJEMesh(resource);
        mesh.setup();
    }

    @Override
    public void onTransformChange() {
        if (mesh!=null)
            mesh.setVertexesPosition(getGameObject().getModelViewMatrix());
    }

}
