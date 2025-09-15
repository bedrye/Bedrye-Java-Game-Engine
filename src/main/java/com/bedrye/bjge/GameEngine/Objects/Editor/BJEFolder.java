package com.bedrye.bjge.GameEngine.Objects.Editor;

import com.bedrye.Objects.GameScene;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Util.*;
import imgui.ImGui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class BJEFolder extends BJEResource {
    public BJEFolder(String path,String name) {
        super(path,name);
        //init(null);
    }
    private HashMap<String,BJEResource> structure = new HashMap<>();
    public BJEFolder Parent;
    public void init(BJEFolder parent){
        this.Parent =parent;
        File a =  new File(path);
        for (File file: a.listFiles()) {

            if(!structure.containsKey(file.getPath())) {
                BJEResource bjeResource = null;
                if (file.isDirectory()) {
                    bjeResource = new BJEFolder(file.getPath(),file.getName());
                    ((BJEFolder) bjeResource).init(this);
                    structure.put(bjeResource.getPath(), bjeResource);

                    continue;
                }
                String fileName = file.getName();
                System.out.println(fileName);
                int index = fileName.lastIndexOf('.');
                String ext= "";
                if(index > 0) {
                    ext = fileName.substring(index + 1);
                }
                if ( ext.equals("png")) {

                    bjeResource = new BJETexture(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);
                    continue;
                }
                if (ext.equals("jpg")){
                    bjeResource = new BJEJPGTexture(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);
                    continue;

                }

                if ( ext.equals("obj")) {
                    bjeResource = new BJEObjFile(file.getPath(),file.getName());
                    structure.put(bjeResource.getPath(), bjeResource);
                    continue;
                }

                bjeResource = new BJEObjFile(file.getPath(),file.getName());
                structure.put(bjeResource.getPath(), bjeResource);
            }

        }


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

        ImGui.pushID(path);
        ImGui.selectable(path);
        if (ImGui.isItemClicked())
        {
            ((GameScene) EngineWindowManager.getInstance().getActiveScene()).bjeAssetHierarchy.setSelectedFolder(this);
        }


    }




}
