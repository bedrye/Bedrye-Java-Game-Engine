package com.bedrye.bjge.GameEngine.Objects.Editor;


import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Util.*;
import imgui.ImGui;

import java.io.File;
import java.util.HashMap;

public class BJEFolder extends BJEResource {
    public BJEFolder(String path,String name) {
        super(path,name);

    }
    private HashMap<String,BJEResource> structure = new HashMap<>();

    public BJEFolder getParent() {
        return parent;
    }


    private BJEFolder parent;
    public void init(BJEFolder parent){
        this.parent =parent;
        File a =  new File(getPath());
        for (File file: a.listFiles()) {

            if(!structure.containsKey(file.getPath())) {
                BJEResource bjeResource = null;
                if (file.isDirectory()) {
                    bjeResource = new BJEFolder(file.getPath(),file.getName());
                    ((BJEFolder) bjeResource).init(this);
                    structure.put(bjeResource.getPath(), bjeResource);

                    continue;
                }
                String ext = getExtention(file);
                if ( ext.equals("png")) {

                    bjeResource = new BJETexture(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);

                }
                else if (ext.equals("jpg")){
                    bjeResource = new BJEJPGTexture(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);


                }

                else if ( ext.equals("obj")) {
                    bjeResource = new BJEObjFile(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);

                }
                else {
                    bjeResource = new BJETextFile(file.getPath(), file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);
                }
            }

        }


    }
    private String getExtention(File file){
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        String ext= "";
        if(index > 0) {
            ext = fileName.substring(index + 1);
        }
        return ext;

    }
 public void showContents(){
        for (var resource: structure.values()) {
            resource.show();
            resource.DragStart();
            resource.hide();
        }




    }
    @Override
    public void show(){

        ImGui.pushID(getPath());
        ImGui.selectable(getPath());
        if (ImGui.isItemClicked())
        {
            ((BJEEditorScene) EngineWindowManager.getInstance().getActiveScene()).bjeAssetHierarchy.setSelectedFolder(this);
        }


    }




}
