package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import imgui.ImGui;

import java.io.File;

public class BJESceneFile extends BJEResource{

    public BJESceneFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }


    @Override
    public void show(){

        ImGui.pushID(getName());
        ImGui.image(((BJETexture) EngineWindowManager.getInstance().getBjeResourceManager().getByName("scenebutton.png")).getTextureID(),12,12);
        ImGui.sameLine();
        ImGui.selectable(getName());


        if (ImGui.isItemClicked())
        {
            EngineWindowManager.getInstance().loadScene(new File(getPath()));
        }


    }
}
