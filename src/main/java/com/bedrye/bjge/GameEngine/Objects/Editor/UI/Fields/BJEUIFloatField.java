package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIFloatField implements BJEUIField,BJEUISettableField {
    String prefix;
    ImFloat imFloat;
    Field field;
    Object o;
    public BJEUIFloatField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix=field.getType().getSimpleName() + " " + field.getName();
        imFloat= new ImFloat(field.getFloat(o));


    }
    @Override
    public void showField() {
        ImGui.inputFloat(prefix, imFloat);
        if(isChanged()){
           // EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectFieldChangeCommand<>(field, o, imFloat.get(),this::setField));
        }
    }


    public boolean isChanged() {
        try {
            return imFloat.get()!=field.getFloat(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setField(Object o) {
        imFloat.set((Float) o);
    }
}
