package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;
import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;
import com.fasterxml.jackson.annotation.*;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // store actual subclass type
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        // register concrete subclasses of Scene
        @JsonSubTypes.Type(value = BJEGameScene.class),
        @JsonSubTypes.Type(value = BJEEditorScene.class)
})

public abstract class Scene implements IGameSpace {
    private Camera camera;
    private boolean initialized = false;
    private List<Object3DAbstract> gameObjects= new ArrayList<>();

    @JsonIgnore
    private ShaderProgram shaderProgram;
    public Scene(){
        this.camera = null;
    }
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public abstract void update();

    public void setGameObjects(List<Object3DAbstract> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public abstract void initialize() ;

    public void setAsParent(Object3DAbstract gameObject) {
        if(!gameObjects.contains(gameObject)){

            gameObjects.add(gameObject);

            gameObject.getParent().getChildList().remove(gameObject);
            gameObject.setParent(this);
        }
    }
    public List<Object3DAbstract> getChildList() {
        return gameObjects;
    }
    public final void addChildObject(Object3DAbstract child){
        if(isInitialized()){
            child.initialize();
        }
        if(!gameObjects.contains(child)) {
            child.getParent().removeChildObject(child);
            child.setParent(this);
            gameObjects.add(child);


        }

    }
    public final void removeChildObject(Object3DAbstract gameObject){
        gameObjects.remove(gameObject);

    }
    public abstract void updateUILayer();
    @JsonIgnore
    public final boolean isFinal(){
        return true;
    }
    public final boolean hasChildren() {return !gameObjects.isEmpty();}

    public void render(){
        getChildList().forEach(Object3DAbstract::render);
    }
    public void fixedUpdate(){
        getChildList().forEach(Object3DAbstract::fixedUpdate);
    }
    public void renderGizmos(){

    }
    public void preRender(){
        glClearColor(0.8f,0.8f,0.8f,1.0f);

        glClear(GL_COLOR_BUFFER_BIT);
        getShaderProgram().Run();
        getShaderProgram().uploadFloat( 0f, "directionalLight.intensity" );
        EngineWindowManager.getInstance().getActiveScene().getShaderProgram().uploadVec3f(new Vector3f(),"ambientLight");
        glActiveTexture(GL_TEXTURE0);
        getChildList().forEach(Object3DAbstract::preRender);
    }
}
