package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;

public class BJEUIPush implements BJEUIField{
    String text;

    public BJEUIPush(String text) {
        this.text = text;
    }


    @Override
    public void showField() {
        ImGui.pushID(text);
    }
}
