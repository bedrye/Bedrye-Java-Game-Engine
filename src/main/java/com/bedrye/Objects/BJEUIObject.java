package com.bedrye.Objects;

import imgui.ImGui;
import org.joml.Vector3f;

public class BJEUIObject extends Object3DAbstract {
    @Override
    public void initialize() {

    }

    @Override
    public void initialize(Vector3f position) {

    }

    @Override
    public void update() {
        ImGui.begin("Title");
            if(ImGui.button("cooool button"))
                ImGui.text("cooool Text");


        ImGui.end();
    }
}
