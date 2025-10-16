package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIFloatField implements BJEUIField {

    ImFloat imFloat;
    Field field;
    Object o;
    public BJEUIFloatField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;

        imFloat= new ImFloat(field.getFloat(o));


    }
    @Override
    public void showField(String prefix) {
        ImGui.inputFloat(prefix, imFloat);
    }

    @Override
    public boolean isChanged() {
        try {
            return imFloat.get()!=field.getFloat(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
