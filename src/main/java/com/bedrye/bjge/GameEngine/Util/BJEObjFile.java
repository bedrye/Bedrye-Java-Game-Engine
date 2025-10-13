package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import imgui.ImGui;

public class BJEObjFile extends BJEResource{

    public BJEObjFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }
    @Override
    public void show() {

        ImGui.pushID(getName());
        ImGui.image(((BJETexture) EngineWindowManager.getInstance().getBjeResourceManager().getByName("3dobjectIcon.png")).getTextureID(), 12, 12);
        ImGui.sameLine();
        ImGui.selectable(getName());
    }


}
