package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import imgui.ImGui;

public class BJESceneFile extends BJEResource{

    public BJESceneFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }


    @Override
    public void show(){

        ImGui.pushID(getName());
        ImGui.image(((BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("scenebutton.png")).getTextureID(),12,12);
        ImGui.sameLine();
        ImGui.selectable(getName());


        if (ImGui.isItemClicked())
        {
            EngineManager.getInstance().loadScene(this);
        }


    }
}
