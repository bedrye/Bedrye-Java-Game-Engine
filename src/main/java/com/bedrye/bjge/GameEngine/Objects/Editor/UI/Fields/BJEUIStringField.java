package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;
import imgui.type.ImDouble;
import imgui.type.ImString;

import java.lang.reflect.Field;

public class BJEUIStringField implements BJEUIField {

    ImString imString;
    Field field;
    Object o;
    public BJEUIStringField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;

        imString= new ImString((String)field.get(o));


    }
    @Override
    public void showField(String prefix) {
        ImGui.inputText(prefix, imString);
    }

    @Override
    public boolean isChanged() {
        try {
            return imString.get()!=field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
