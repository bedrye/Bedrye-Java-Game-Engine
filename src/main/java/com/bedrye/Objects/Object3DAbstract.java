package com.bedrye.Objects;


import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class Object3DAbstract {

    private Vector3f position;
    private Vector3f globalPosition;
    private Vector3f rotation;
    private Vector3f scale;
    private Object3DAbstract parent;
    private String name;
    private Matrix4f transform = new Matrix4f();


    public Object3DAbstract(Object3DAbstract parent){
        position= new Vector3f(0,0,0);
        rotation= new Vector3f(0,0,0);
        globalPosition = new Vector3f(0,0,0);
        scale= new Vector3f(1,1,1);
        childList = new ArrayList<>();
        scripts = new ArrayList<>();
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
        globalPosition = new Vector3f(0,0,0);
        scale= new Vector3f(1,1,1);
        childList = new ArrayList<>();
        scripts = new ArrayList<>();

    }

    public final List<Object3DAbstract> getChildList() {
        return childList;
    }
    public final Vector3f getPosition(){
        return globalPosition;}
    public final Vector3f getLocalPos() {
        return position;
    }
    public void setLocalPos(Vector3f position) {
        this.position = position;
        updateTransform();
    }
    public final void setLocalPosX(float x){
        this.position.x = x;
        updateTransform();
    }
    public final void setLocalPosY(float y){
        this.position.y = y;
        updateTransform();
    }
    public final void setLocalPosZ(float z){
        this.position.z = z;
        updateTransform();
    }
    public final float getLocalPosX(){return position.x;}
    public final float getLocalPosY(){
        return position.y;
    }
    public final float getLocalPosZ(){
        return position.z;
    }
    public final void setRotationX(float x){
        this.rotation.x = x;
        updateTransform();
    }
    public final void setRotationY(float y){
        this.rotation.y = y;
        updateTransform();
    }
    public final void setRotationZ(float z){
        this.rotation.z = z;
        updateTransform();
    }
    public final float getRotationX(){
        return this.rotation.x;
    }
    public final float getRotationY(){
        return this.rotation.y;
    }
    public final float getRotationZ(){
        return this.rotation.z;
    }
    public void addChild(Object3DAbstract child){
        if(!childList.contains(child)&&child!=this) {
            child.setParent(this);
            childList.add(child);
        }
    }
    public void removeChild(Object3DAbstract child){
        if(!childList.contains(child)) {
            child.setParent(null);
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
        updateTransform();
    }

    public final Vector3f getScale() {
        return scale;
    }

    public final void setScale(Vector3f scale) {
        this.scale = scale;
        updateTransform();
    }
    public final void addScript(MainBehaviour mainBehaviour){mainBehaviour.setGameObject(this);scripts.add(mainBehaviour); }
    public final <T extends MainBehaviour> void removeScript(Class<T> tClass){
        for (int i = 0; i < scripts.size(); i++) {
        MainBehaviour script = scripts.get(i);
        if(tClass.isAssignableFrom(script.getClass())){
            scripts.remove(i);
            return;
            }
        }
    }
    public final void removeScript(int mainBehaviour){scripts.remove(mainBehaviour);}
    public final List<MainBehaviour> getScriptList(){
        return scripts;
    }
    public final Object3DAbstract getParent() {
        return parent;
    }
    public final <T extends MainBehaviour> T getScript(Class<T> tClass){
        for (MainBehaviour script:scripts) {
            if(tClass.isAssignableFrom(script.getClass())){
                return tClass.cast(script);
            }
        }
        return null;

    }

    public final void setParent(Object3DAbstract parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private void updateTransform(){

        transform.identity().
                translate(new Vector3f(getLocalPosX(), getLocalPosY(), getLocalPosZ())).
                rotateX((float)Math.toRadians(getRotationX())).
                rotateY((float)Math.toRadians(getRotationY())).
                rotateZ((float)Math.toRadians(getRotationZ())).
                scale(1);
        if (getParent()!=null)
            transform.mulLocal(getParent().getTransformMatrix());
        transform.getTranslation(globalPosition);
        childList.forEach(Object3DAbstract::updateTransform);
    }
    public Matrix4f getTransformMatrix(){
        return transform;
    }

}
