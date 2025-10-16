package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIBoolField implements BJEUIField {

    ImBoolean imBoolean;
    Field field;
    Object o;
    public BJEUIBoolField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;

        imBoolean= new ImBoolean(field.getBoolean(o));


    }
    @Override
    public void showField(String prefix){ImGui.checkbox(prefix, imBoolean);
    }

    @Override
    public boolean isChanged() {
        try {
            return imBoolean.get()!=field.getBoolean(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
