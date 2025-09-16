package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.Objects.GameScene;
import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Listeners.MouseListener;
import com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs.Cube;
import com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs.Sphere;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            if (ImGui.beginPopupContextItem("context_menu")) // unique identifier
            {
                if (ImGui.beginMenu("Add"))
                {
                    Object3DAbstract ob=null;

                    if (ImGui.menuItem("Cube")){
                        ob = new Cube();
                    }

                    if (ImGui.menuItem("Sphere")){
                        ob = new Sphere();
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
                            ob = new Cube();
                        }

                    if (ImGui.menuItem("Sphere")){
                        ob = new Sphere();
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
    public void AddNewObject(){






    }
}
