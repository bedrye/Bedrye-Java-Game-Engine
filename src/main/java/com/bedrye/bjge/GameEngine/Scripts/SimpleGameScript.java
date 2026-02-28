package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Objects.Object3D;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.BJEObjFile;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectCreateCommand;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class SimpleGameScript extends MainBehaviour{

    private ArrayList<Object3DAbstract> donuts = new ArrayList<>();
    Random r= new Random();
    public int maxDonuts = 10;

    @Override
    public void init(){

        for (int i=0;i<maxDonuts;i++){
            Object3DAbstract o = new Object3D();
            o.setRotationX(-90f);
            o.setRotationZ( r.nextInt()%180);
            o.addScript( new BJEMeshRenderer((BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByName("donut.obj"),
                    (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("donut.png")));
            EngineManager.getInstance().getActiveScene().addTask(new ObjectCreateCommand(EngineManager.getInstance().getActiveScene(),o));
            o.addScript(new SimpleGameScript());
            o.setLocalPos(new Vector3f(r.nextInt()%5,r.nextInt()%5,0));
            o.setScale(new Vector3f(0.3f));
            donuts.add(o);
        }
    }
    public void fixedUpdate(){

        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_W)) {
            getGameObject().setLocalPosY(getGameObject().getLocalPosY() + 0.1f);
            getGameObject().setRotationZ(getGameObject().getRotationZ() - 2f);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_S)) {
            getGameObject().setLocalPosY(  getGameObject().getLocalPosY() - 0.1f);
            getGameObject().setRotationZ(getGameObject().getRotationZ() + 2f);
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_D)) {
            getGameObject().setLocalPosX(  getGameObject().getLocalPosX() + 0.1f);
            getGameObject().setRotationY(getGameObject().getRotationY() + 2f);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_A)) {
            getGameObject().setLocalPosX(  getGameObject().getLocalPosX() - 0.1f);
            getGameObject().setRotationY(getGameObject().getRotationY() - 2f);
        }
        float size =0.3f*  getGameObject().getScale().x;
        for (Object3DAbstract o: donuts) {

            if(o.getLocalPosX()>  getGameObject().getLocalPosX()-size&&o.getLocalPosX()<  getGameObject().getLocalPosX()+size&&o.getLocalPosY()>  getGameObject().getLocalPosY()-size&&o.getLocalPosY()<  getGameObject().getLocalPosY()+size) {
                getGameObject().setScale(  getGameObject().getScale().mul(1.01f));
                o.setLocalPos(new Vector3f(r.nextInt()%12,r.nextInt()%12,0));
            }



        }


    }

}
