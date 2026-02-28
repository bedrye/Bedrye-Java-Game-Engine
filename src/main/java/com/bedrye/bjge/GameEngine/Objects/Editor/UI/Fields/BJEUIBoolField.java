package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIBoolField implements BJEUIField,BJEUISettableField {


    String prefix;
    ImBoolean imBoolean;
    Field field;
    Object o;
    public BJEUIBoolField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix = field.getType().getSimpleName() + " " + field.getName();
        imBoolean= new ImBoolean(field.getBoolean(o));


    }
    @Override
    public void showField(){ImGui.checkbox(prefix, imBoolean);
        if(isChanged()){
          //  EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectFieldChangeCommand<>(field, o, imBoolean.get(),this));
        }
    }


    public boolean isChanged() {
        try {
            return imBoolean.get()!=field.getBoolean(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setField(Object o) {
        imBoolean.set((Boolean) o);
    }

}
