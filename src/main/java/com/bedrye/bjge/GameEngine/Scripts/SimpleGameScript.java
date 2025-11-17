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
    private Object3DAbstract object3D ;
    private ArrayList<Object3DAbstract> Coins = new ArrayList<>();
    Random r= new Random();

    @Override
    public void init(){

        object3D = new Object3D();
        object3D.addScript( new BJEMeshRenderer((BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\sphere.obj"),
                (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));

        EngineManager.getInstance().getActiveScene().addTask(new ObjectCreateCommand(EngineManager.getInstance().getActiveScene(),object3D));
        for (int i=0;i<5;i++){
            Object3DAbstract o = new Object3D();

            o.addScript( new BJEMeshRenderer((BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\sphere.obj"),
                    (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));

            EngineManager.getInstance().getActiveScene().addTask(new ObjectCreateCommand(EngineManager.getInstance().getActiveScene(),o));
            o.setLocalPos(new Vector3f(r.nextInt()%3,r.nextInt()%3,0));
            o.setScale(new Vector3f(0.3f));
            Coins.add(o);
        }
    }
    public void update(){

        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_W)) {
            object3D.setLocalPosY(object3D.getLocalPosY() + 0.1f);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_S)) {
            object3D.setLocalPosY(object3D.getLocalPosY() - 0.1f);
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_D)) {
            object3D.setLocalPosX(object3D.getLocalPosX() + 0.1f);
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_A)) {
            object3D.setLocalPosX(object3D.getLocalPosX() - 0.1f);
        }
        float size =0.3f*object3D.getScale().x;
        for (Object3DAbstract o: Coins) {

            if(o.getLocalPosX()>object3D.getLocalPosX()-size&&o.getLocalPosX()<object3D.getLocalPosX()+size&&o.getLocalPosY()>object3D.getLocalPosY()-size&&o.getLocalPosY()<object3D.getLocalPosY()+size) {
                object3D.setScale(object3D.getScale().mul(1.01f));
                o.setLocalPos(new Vector3f(r.nextInt()%12,r.nextInt()%12,0));
            }



        }


    }

}
