package com.bedrye.bjge.GameEngine.Objects.Editor;


import com.bedrye.bjge.GameEngine.EngineManager;
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
        structure.clear();
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

                if(EngineManager.getInstance().getBjeResourceManager().getByName(file.getName())==null)
                    bjeResource = new BJETextFile(file.getPath(),file.getName());
                    else bjeResource = EngineManager.getInstance().getBjeResourceManager().getByName(file.getName());
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

        ImGui.pushID(getName());
        ImGui.image(((BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("folderbutton.png")).getTextureID(),12,12);
        ImGui.sameLine();
        ImGui.selectable(getName());


        if (ImGui.isItemClicked())
        {
            EngineManager.getInstance().getBjeResourceManager().setCurrentPath(this);
        }


    }




}
