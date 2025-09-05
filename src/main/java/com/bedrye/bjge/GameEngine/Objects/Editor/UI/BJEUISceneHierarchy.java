package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.Objects.GameScene;
import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BJEUISceneHierarchy extends BJEUIWindow{


    HashMap<Object3DAbstract,Object3DAbstract> changes = new HashMap<>();
    public void update() {
        ImGui.begin("Hierarchy");
        int flag = ImGuiTreeNodeFlags.DefaultOpen;
        if (ImGui.treeNodeEx("root", flag))
        {

            for (Object3DAbstract object3DAbstract : EngineWindowManager.getInstance().getActiveScene().getGameObjects())
                AddTreeNode(object3DAbstract);


            ImGui.treePop();
        }
        ImGui.end();
        changes.forEach((k, v) -> k.addChild(v));
        changes.clear();

    }
    public void AddTreeNode(Object3DAbstract object3DAbstract){
        int flag = ImGuiTreeNodeFlags.OpenOnArrow;
        if (!object3DAbstract.hasChildren())
        {
            flag |= ImGuiTreeNodeFlags.Leaf;
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

            for (Object3DAbstract obj : object3DAbstract.getChildList())
                AddTreeNode(obj);




            ImGui.treePop();
        }


    }
}
