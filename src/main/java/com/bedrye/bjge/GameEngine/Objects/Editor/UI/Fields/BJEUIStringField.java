package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImDouble;
import imgui.type.ImString;

import java.lang.reflect.Field;

public class BJEUIStringField implements BJEUIField,BJEUISettableField {
    String prefix;
    ImString imString;
    Field field;
    Object o;
    public BJEUIStringField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix=field.getType().getSimpleName() + " " + field.getName();
        imString= new ImString((String)field.get(o));


    }
    @Override
    public void showField() {
        ImGui.inputText(prefix, imString);
        if(isChanged()){
            //EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectFieldChangeCommand<>(field, o, imString.get(),this));
        }
    }


    public boolean isChanged() {
        try {
            return imString.get()!=field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setField(Object o) {
        imString.set((String) o);
    }
}
