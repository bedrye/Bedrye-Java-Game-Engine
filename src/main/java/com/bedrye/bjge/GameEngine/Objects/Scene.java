package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;
import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;
import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

}
