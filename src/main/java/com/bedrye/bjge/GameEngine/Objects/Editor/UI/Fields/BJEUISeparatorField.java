package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;

public class BJEUISeparatorField implements BJEUIField{



    @Override
    public void showField() {
        ImGui.separator();
    }
}
