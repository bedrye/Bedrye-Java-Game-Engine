package com.bedrye.Objects;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.Scripts.MainBehaviour;

import java.util.ArrayList;
import java.util.List;

public abstract class Object3DAbstract {
    private Vec3 position;
    private Vec3 rotation;
    private Vec3 scale;

    private List<Object3DAbstract> childList;

    private List<MainBehaviour> scripts;
    public abstract void initialize();

    public abstract void initialize(Vec3 position);

    public Object3DAbstract(){
        position= new Vec3(0,0,0);
        rotation= new Vec3(0,0,0);
        scale= new Vec3(1,1,1);
        childList = new ArrayList<>();
        scripts = new ArrayList<>();

    }

    public List<Object3DAbstract> getChildList() {
        return childList;
    }
    public Vec3 getPosition() {
        return position;
    }
    public void setPosition(Vec3 position) {
        this.position = position;
    }
    public void setX(float x){
        this.position.x = x;
    }
    public void setY(float y){
        this.position.y = y;
    }
    public void setZ(float z){
        this.position.z = z;
    }
    public float getX(){
        return this.position.x;
    }
    public float getY(){
        return this.position.y;
    }
    public float getZ(){
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


    public Vec3 getRotation() {
        return rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public Vec3 getScale() {
        return scale;
    }

    public void setScale(Vec3 scale) {
        this.scale = scale;
    }
    public void addScript(MainBehaviour mainBehaviour){scripts.add(mainBehaviour);}
    public void removeScript(MainBehaviour mainBehaviour){scripts.remove(mainBehaviour);}
    public void removeScript(int mainBehaviour){scripts.remove(mainBehaviour);}

}
