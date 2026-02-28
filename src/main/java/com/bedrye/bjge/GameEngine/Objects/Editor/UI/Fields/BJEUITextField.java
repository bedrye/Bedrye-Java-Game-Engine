package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;

public class BJEUITextField implements BJEUIField{
    String text;

    public BJEUITextField(String text) {
        this.text = text;
    }

    @Override
    public void showField() {
        ImGui.text(text);
    }
}
