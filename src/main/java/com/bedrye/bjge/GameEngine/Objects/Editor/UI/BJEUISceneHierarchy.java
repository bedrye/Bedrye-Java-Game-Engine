package com.bedrye.bjge.GameEngine.Objects.Editor.UI;


import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.*;
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

public class BJEUISceneHierarchy extends BJEUIWindow{


    ICommand command ;
    public void update() {
        ImGui.begin("Hierarchy");
        int flag = ImGuiTreeNodeFlags.DefaultOpen;
        if (ImGui.treeNodeEx("root", flag))
        {

            showContextMenu(EngineManager.getInstance().getActiveScene());
            if (ImGui.beginDragDropTarget()) {
                Object3DAbstract object3DAbstract1 = ImGui.acceptDragDropPayload("SceneHierarchy");

                if (object3DAbstract1 != null)
                    EngineManager.getInstance().getActiveScene().setAsParent(object3DAbstract1);


                ImGui.endDragDropTarget();



            }
            for (Object3DAbstract object3DAbstract : EngineManager.getInstance().getActiveScene().getChildList())
                addTreeNode(object3DAbstract);



            ImGui.treePop();

        }
        ImGui.end();
        if(command!=null) {
            EngineManager.getInstance().getBjeCommandManager().executeCommand(command);
            command=null;
        }



    }
    public void addTreeNode(Object3DAbstract object3DAbstract){
        int flag = ImGuiTreeNodeFlags.OpenOnArrow;
        if (!object3DAbstract.hasChildren())
        {
            flag |= ImGuiTreeNodeFlags.Leaf;
        }
        if (object3DAbstract==((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.getObject3DAbstract())
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
                (BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\cube.obj"),
                (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));
        return  ob;


    }
    private Object3D createPlane(){
        Object3D ob = new Object3D();
        ob.setName("Plane");
        ob.addScript(new BJEMeshRenderer(
                (BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\plane.obj"),
                (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));
        return  ob;


    }

    private Object3D createCylinder(){
        Object3D ob = new Object3D();
        ob.setName("Cylinder");
        ob.addScript(new BJEMeshRenderer(
                (BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\cylinder.obj"),
                (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));
        return  ob;


    }

    private Object3D createSphere(){
        Object3D ob = new Object3D();
        ob.setName("Sphere");
        ob.addScript(new BJEMeshRenderer(
                (BJEObjFile) EngineManager.getInstance().getBjeResourceManager().getByPath("INTERNAL\\sphere.obj"),
                (BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("whitetexture.png")));

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
                if (ImGui.menuItem("Plane")) {
                    ob = createPlane();

                }
                if (ImGui.menuItem("Cylinder")) {
                    ob = createCylinder();

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
                if (ImGui.menuItem("SkyColor")) {
                    ob = new SkyColor();

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
