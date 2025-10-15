package com.bedrye.bjge.GameEngine.Objects.Editor.UI;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Objects.*;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Util.BJEMaterial;
import com.bedrye.bjge.GameEngine.Util.BJEMesh;
import com.bedrye.bjge.GameEngine.Util.BJEObjFile;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectChangeParentCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectCreateCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectDeleteCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectSelectCommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.HashMap;

public class BJEUISceneHierarchy extends BJEUIWindow{


    ICommand command ;
    public void update() {
        ImGui.begin("Hierarchy");
        int flag = ImGuiTreeNodeFlags.DefaultOpen;
        if (ImGui.treeNodeEx("root", flag))
        {

            showContextMenu(EngineWindowManager.getInstance().getActiveScene());
            if (ImGui.beginDragDropTarget()) {
                Object3DAbstract object3DAbstract1 = ImGui.acceptDragDropPayload("SceneHierarchy");

                if (object3DAbstract1 != null)
                    EngineWindowManager.getInstance().getActiveScene().setAsParent(object3DAbstract1);


                ImGui.endDragDropTarget();



            }
            for (Object3DAbstract object3DAbstract : EngineWindowManager.getInstance().getActiveScene().getChildList())
                addTreeNode(object3DAbstract);



            ImGui.treePop();

        }
        ImGui.end();
        if(command!=null) {
            EngineWindowManager.getInstance().getBjeCommandManager().executeCommand(command);
            command=null;
        }



    }
    public void addTreeNode(Object3DAbstract object3DAbstract){
        int flag = ImGuiTreeNodeFlags.OpenOnArrow;
        if (!object3DAbstract.hasChildren())
        {
            flag |= ImGuiTreeNodeFlags.Leaf;
        }
        if (object3DAbstract==((BJEEditorScene)EngineWindowManager.getInstance().getActiveScene()).inspector.getObject3DAbstract())
        {
            flag |= ImGuiTreeNodeFlags.Selected;
        }
        if (ImGui.treeNodeEx( object3DAbstract.hashCode(),flag,object3DAbstract.getName()))
        {
            if (ImGui.isItemClicked())
            {
               command = new ObjectSelectCommand(object3DAbstract);
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
                        command = new ObjectChangeParentCommand(object3DAbstract,object3DAbstract1);



            ImGui.endDragDropTarget();
            }
            showContextMenu(object3DAbstract);
            for (Object3DAbstract obj : object3DAbstract.getChildList())
                addTreeNode(obj);




            ImGui.treePop();
        }


    }
    private Object3D createCube(){
        Object3D ob = new Object3D();
        ob.setName("Cube");
        ob.addScript(new BJEMeshRenderer(
                new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Cube.obj","Cube.obj"),
                (BJETexture) EngineWindowManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));
        return  ob;


    }

    private Object3D createSphere(){
        Object3D ob = new Object3D();
        ob.setName("Sphere");
        ob.addScript(new BJEMeshRenderer(new BJEObjFile("O:\\GIt\\MultiplayerShooter\\Assets\\Sphere.obj","Sphere.obj"),
                (BJETexture) EngineWindowManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));

        return ob;
    }
    private void showContextMenu(IGameSpace object3DAbstract){

        if (ImGui.beginPopupContextItem("context_menu")) {
            if (ImGui.beginMenu("Add")) {
                Object3DAbstract ob = null;

                if (ImGui.menuItem("Cube")) {
                    ob = createCube();
                }

                if (ImGui.menuItem("Sphere")) {
                    ob = createSphere();

                }
                if (ImGui.beginMenu("Lights")) {
                    if (ImGui.menuItem("Ambient")) {
                        ob = new AmbientLight();
                    }

                    if (ImGui.menuItem("Directional")) {
                        ob = new DirectionalLight();
                    }
                    ImGui.endMenu();
                }
                if (ImGui.menuItem("Camera")) {
                    ob = new BJECameraStart();

                }
                if (ob != null) {
                    command = new ObjectCreateCommand(object3DAbstract,ob);

                }


                ImGui.endMenu();


            }
            if (ImGui.menuItem("Delete",null,false,!object3DAbstract.isFinal()))
            {
                command = new ObjectDeleteCommand((Object3DAbstract) object3DAbstract);
            }
            ImGui.endPopup();
        }

    }

}
