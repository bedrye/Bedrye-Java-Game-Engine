package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIIntField implements BJEUIField {

    ImInt imInt;
    Field field;
    Object o;
    public BJEUIIntField(Field field,Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;

        imInt= new ImInt(field.getInt(o));


    }
    @Override
    public void showField(String prefix) {
        ImGui.inputInt(prefix, imInt);
    }

    @Override
    public boolean isChanged() {
        try {
            return imInt.get()!=field.getInt(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
