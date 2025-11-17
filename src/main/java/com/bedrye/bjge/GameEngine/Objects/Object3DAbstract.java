package com.bedrye.bjge.GameEngine.Objects;



import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Annotation.EditorBehaviour;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)

public abstract class Object3DAbstract implements IGameSpace {

    private Vector3f position;
    private Vector3f globalPosition;
    private Vector3f rotation;
    private Vector3f scale;
    private IGameSpace parent;
    private String name;
    @JsonIgnore
    private Matrix4f transform = new Matrix4f();

    public Object3DAbstract(IGameSpace parent){
        position= new Vector3f(0,0,0);
        rotation= new Vector3f(0,0,0);
        globalPosition = new Vector3f(0,0,0);
        scale= new Vector3f(1,1,1);
        name = this.getClass().getSimpleName();
        this.parent=parent;
    }

    private List<Object3DAbstract> childList =new ArrayList<>();

    private List<MainBehaviour> scripts =new ArrayList<>();
    public void initialize(){

        for (MainBehaviour mainBeh:getScriptList())
            if(mainBeh.getClass().isAnnotationPresent(EditorBehaviour.class))
                getScriptList().forEach(MainBehaviour::init);
        getChildList().forEach(Object3DAbstract::initialize);
    }


    public void initialize(Vector3f position) {


        for (MainBehaviour mainBeh:getScriptList())
            if(isActive(mainBeh))
                mainBeh.init();
        getChildList().forEach(Object3DAbstract::initialize);
    }


    public void update() {
        for (MainBehaviour mainBeh:getScriptList()) {
            if(isActive(mainBeh))
                mainBeh.update();

        }
        getChildList().forEach(Object3DAbstract::update);

    }

    public Object3DAbstract(){
        this(EngineManager.getInstance().getActiveScene());


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
    public void addChildObject(Object3DAbstract child){
        if(!childList.contains(child)&&child!=this&&!isParent(child)) {
                child.parent.removeChildObject(child);

            child.setParent(this);
            childList.add(child);
            child.updateTransform();

        }
    }
    public boolean isParent(Object3DAbstract object3DAbstract){

        if(object3DAbstract == this)
            return true;
        if(parent.isFinal())
            return false;
        return ((Object3DAbstract) this.parent).isParent(object3DAbstract);

    }
    public void removeChildObject(Object3DAbstract child){
        if(childList.contains(child)) {

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
    public final void addScript(MainBehaviour mainBehaviour){mainBehaviour.setGameObject(this);scripts.add(mainBehaviour);
        mainBehaviour.onTransformChange();}
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
    public final IGameSpace getParent() {
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

    public final void setParent(IGameSpace parent) {
        this.parent = parent;
        updateTransform();
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
                scale(scale.x,scale.y,scale.z);
        if (!getParent().isFinal())
            transform.mulLocal(((Object3DAbstract) getParent()).getTransformMatrix());
        transform.getTranslation(globalPosition);

        childList.forEach(Object3DAbstract::updateTransform);
        scripts.forEach(MainBehaviour::onTransformChange);
    }
    @JsonIgnore
    public Matrix4f getTransformMatrix(){
        return transform;
    }
    @JsonIgnore
    public Matrix4f getModelViewMatrix(){
        return  new Matrix4f(EngineManager.getInstance().getActiveScene().getCamera().getViewMatrix()).mul(transform);
    }
    public boolean hasChildren(){
        return !childList.isEmpty();



    }
    public void delete(){
            if(getParent()!=null) {
                getParent().removeChildObject(this);
            }
            else
                EngineManager.getInstance().getActiveScene().getChildList().remove(this);

        ((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.setObject3DAbstract(null);


        }
        public void gameUpdate(){
        update();
        }
    public void gameInitialize(){
        initialize();
    }
    public final void removeScript(MainBehaviour mainBehaviour){scripts.remove(mainBehaviour);}
    @JsonIgnore
    public final boolean isFinal(){
        return false;
    }
    public void render(){
        for (MainBehaviour mainBeh:getScriptList())
            if(isActive(mainBeh))
                mainBeh.render();
        getChildList().forEach(Object3DAbstract::render);
    }
    public void fixedUpdate(){
        for (MainBehaviour mainBeh:getScriptList())
            if(isActive(mainBeh))
                mainBeh.fixedUpdate();
        getChildList().forEach(Object3DAbstract::fixedUpdate);
    }
    public void preRender(){
        for (MainBehaviour mainBeh:getScriptList())
            if(isActive(mainBeh))
                mainBeh.fixedUpdate();
        getChildList().forEach(Object3DAbstract::preRender);

    }

    private boolean isActive(MainBehaviour mainBehaviour){
        return mainBehaviour.isActive()&&(
                (EngineManager.getInstance().isInEditMode()&& mainBehaviour.getClass().isAnnotationPresent(EditorBehaviour.class))
                        ||!EngineManager.getInstance().isInEditMode());

    }

}



