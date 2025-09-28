package com.bedrye.bjge.GameEngine.Objects.Editor.UI;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.*;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs.Sphere;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Util.BJEObjFile;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.ArrayList;
import java.util.HashMap;

public class BJEUISceneHierarchy extends BJEUIWindow{


    HashMap<Object3DAbstract,Object3DAbstract> changes = new HashMap<>();
    ArrayList<Object3DAbstract> toDelete =new ArrayList<>();
    public void update() {
        ImGui.begin("Hierarchy");
        int flag = ImGuiTreeNodeFlags.DefaultOpen;
        if (ImGui.treeNodeEx("root", flag))
        {


            if (ImGui.beginDragDropTarget()) {
                Object3DAbstract object3DAbstract1 = ImGui.acceptDragDropPayload("SceneHierarchy");

                if (object3DAbstract1 != null)
                    EngineWindowManager.getInstance().getActiveScene().setAsParent(object3DAbstract1);
                //object3DAbstract.addChild(object3DAbstract1);


                ImGui.endDragDropTarget();
            }
            if (ImGui.beginPopupContextItem("context_menu"))
            {
                if (ImGui.beginMenu("Add"))
                {
                    Object3DAbstract ob=null;

                    if (ImGui.menuItem("Cube")){
                        ob = createCube();
                    }

                    if (ImGui.menuItem("Sphere")){
                        ob = createCube();

                    }
                    if (ImGui.beginMenu("Lights"))
                    {
                        if (ImGui.menuItem("Ambient")){
                            ob = new AmbientLight();
                        }

                        if (ImGui.menuItem("Directional")){
                            ob = new DirectionalLight();
                        }
                        ImGui.endMenu();
                    }

                    if(ob!=null){
                        EngineWindowManager.getInstance().getActiveScene().addGameObject(ob);

                    }


                    ImGui.endMenu();
                }
                ImGui.endPopup();
            }
            for (Object3DAbstract object3DAbstract : EngineWindowManager.getInstance().getActiveScene().getGameObjects())
                AddTreeNode(object3DAbstract);



            ImGui.treePop();

        }
        ImGui.end();
        changes.forEach((k, v) -> k.addChild(v));

        changes.clear();

        toDelete.forEach(k -> k.delete());

        toDelete.clear();

    }
    public void AddTreeNode(Object3DAbstract object3DAbstract){
        int flag = ImGuiTreeNodeFlags.OpenOnArrow;
        if (!object3DAbstract.hasChildren())
        {
            flag |= ImGuiTreeNodeFlags.Leaf;
        }
        if (object3DAbstract==((GameScene)EngineWindowManager.getInstance().getActiveScene()).inspector.getObject3DAbstract())
        {
            flag |= ImGuiTreeNodeFlags.Selected;
        }
        if (ImGui.treeNodeEx( object3DAbstract.hashCode(),flag,object3DAbstract.getName()))
        {
            if (ImGui.isItemClicked())
            {
                ((GameScene)EngineWindowManager.getInstance().getActiveScene()).inspector.setObject3DAbstract(object3DAbstract);
            }
            if (ImGui.beginDragDropSource())
            {
                ImGui.setDragDropPayload("SceneHierarchy",object3DAbstract);
                ImGui.text(object3DAbstract.getName());

                ImGui.endDragDropSource();


            }
            if (ImGui.beginDragDropTarget()) {
                Object3DAbstract object3DAbstract1 = ImGui.acceptDragDropPayload("SceneHierarchy");

                    if(object3DAbstract1!=null)
                        changes.put(object3DAbstract,object3DAbstract1);
                    //object3DAbstract.addChild(object3DAbstract1);


            ImGui.endDragDropTarget();
            }
            if (ImGui.beginPopupContextItem("context_menu")) // unique identifier
            {
                if (ImGui.beginMenu("Add"))
                {
                    Object3DAbstract ob=null;

                        if (ImGui.menuItem("Cube")){
                            ob = createCube();
                        }

                    if (ImGui.menuItem("Sphere")){
                        ob = createSphere();
                    }
                    if (ImGui.beginMenu("Lights"))
                    {
                        if (ImGui.menuItem("Ambient")){
                            ob = new AmbientLight();
                        }

                        if (ImGui.menuItem("Directional")){
                            ob = new DirectionalLight();
                        }
                        ImGui.endMenu();
                    }
                    if(ob!=null){
                        object3DAbstract.addChild(ob);
                        ob.initialize();
                    }


                    ImGui.endMenu();
                }
                if (ImGui.menuItem("Delete"))
                {
                    toDelete.add(object3DAbstract);
                }
                ImGui.endPopup();
            }
            for (Object3DAbstract obj : object3DAbstract.getChildList())
                AddTreeNode(obj);




            ImGui.treePop();
        }


    }
    private Object3D createCube(){
        Object3D ob = new Object3D();
        ob.setName("Cube");
        ob.addScript(new BJEMeshRenderer(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Cube.obj","Cube.obj"),new BJETexture("Assets\\Egg.png","Egg.png")));
        return  ob;


    }

    private Object3D createSphere(){
        Object3D ob = new Object3D();
        ob.setName("Sphere");
        ob.addScript(new BJEMeshRenderer(new BJEMesh(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Sphere.obj","Sphere.obj")),new BJEMaterial(new BJETexture("Assets\\Egg.png","Egg.png"))));

        return ob;
    }

}
