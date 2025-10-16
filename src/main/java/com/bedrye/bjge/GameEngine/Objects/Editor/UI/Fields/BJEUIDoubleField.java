package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import imgui.ImGui;
import imgui.type.ImDouble;
import imgui.type.ImFloat;

import java.lang.reflect.Field;

public class BJEUIDoubleField implements BJEUIField {

    ImDouble imDouble;
    Field field;
    Object o;
    public BJEUIDoubleField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;

        imDouble= new ImDouble(field.getDouble(o));


    }
    @Override
    public void showField(String prefix) {
        ImGui.inputDouble(prefix, imDouble);
    }

    @Override
    public boolean isChanged() {
        try {
            return imDouble.get()!=field.getDouble(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
