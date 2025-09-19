package com.bedrye.bjge.GameEngine.Objects;


import com.bedrye.Objects.Camera;
import com.bedrye.Objects.GameScene;
import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // store actual subclass type
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        // register concrete subclasses of Scene
        @JsonSubTypes.Type(value = EditorScene.class),
        @JsonSubTypes.Type(value = GameScene.class)
})

public abstract class Scene {
    private Camera camera;
    private boolean initialized = false;
    private ArrayList<Object3DAbstract> gameObjects= new ArrayList<>();

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
            gameObject.setParent(null);
        }
    }
    public ArrayList<Object3DAbstract> getGameObjects() {
        return gameObjects;
    }
    public final void addGameObject(Object3DAbstract gameObject){
        if(isInitialized()){
            gameObject.initialize();

        }
        gameObjects.add(gameObject);

    }
    public abstract void updateUILayer();

}
