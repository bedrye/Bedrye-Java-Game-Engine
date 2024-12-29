package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class Object3DAbstract {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Object3DAbstract parent;

    public Object3DAbstract(Object3DAbstract parent){
        this.parent=parent;
    }

    private List<Object3DAbstract> childList;

    private List<MainBehaviour> scripts;
    public abstract void initialize();
    public abstract void initialize(Vector3f position);
    public abstract void update();

    public Object3DAbstract(){
        position= new Vector3f(0,0,0);
        rotation= new Vector3f(0,0,0);
        scale= new Vector3f(1,1,1);
        childList = new ArrayList<>();
        scripts = new ArrayList<>();

    }

    public final List<Object3DAbstract> getChildList() {
        return childList;
    }
    public final Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public final void setX(float x){
        this.position.x = x;
    }
    public final void setY(float y){
        this.position.y = y;
    }
    public final void setZ(float z){
        this.position.z = z;
    }
    public final float getX(){
        return this.position.x;
    }
    public final float getY(){
        return this.position.y;
    }
    public final float getZ(){
        return this.position.z;
    }
    public void addChild(Object3DAbstract child){
        if(!childList.contains(child)) {
            childList.add(child);
        }
    }
    public void removeChild(Object3DAbstract child){
        if(!childList.contains(child)) {
            childList.remove(child);
        }
    }
    public Object3DAbstract getChild(int id){
        if(childList.size()<id) throw new IllegalArgumentException("Child index out bounds");
        return childList.get(id);
    }


    public final Vector3f getRotation() {
        return rotation;
    }

    public final void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public final Vector3f getScale() {
        return scale;
    }

    public final void setScale(Vector3f scale) {
        this.scale = scale;
    }
    public final void addScript(MainBehaviour mainBehaviour){mainBehaviour.setGameObject(this);scripts.add(mainBehaviour); }
    public final void removeScript(MainBehaviour mainBehaviour){scripts.remove(mainBehaviour);}
    public final void removeScript(int mainBehaviour){scripts.remove(mainBehaviour);}
    public final List<MainBehaviour> getScriptList(){
        return scripts;
    }
    public final Object3DAbstract getParent() {
        return parent;
    }

    public final void setParent(Object3DAbstract parent) {
        this.parent = parent;
    }

}
