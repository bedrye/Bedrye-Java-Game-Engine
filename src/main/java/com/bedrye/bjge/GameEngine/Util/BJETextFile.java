package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import imgui.ImGui;

public class BJETextFile extends BJEResource{

    public BJETextFile(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);
    }

    @Override
    public void show() {

        ImGui.pushID(getName());
        ImGui.image(((BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("textbutton.png")).getTextureID(), 12, 12);
        ImGui.sameLine();
        ImGui.selectable(getName());
    }


}
