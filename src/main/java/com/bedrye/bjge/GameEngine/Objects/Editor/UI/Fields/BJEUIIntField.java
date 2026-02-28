package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class BJEUIIntField implements BJEUIField,BJEUISettableField {
    String prefix;
    ImInt imInt;
    Field field;
    Object o;
    public BJEUIIntField(Field field,Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix=field.getType().getSimpleName() + " " + field.getName();
        imInt= new ImInt(field.getInt(o));


    }
    @Override
    public void showField() {
        ImGui.inputInt(prefix, imInt);
        if(isChanged()){
            //EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectFieldChangeCommand<>(field, o, imInt.get(),this::setField));
        }
    }


    public boolean isChanged() {
        try {
            return imInt.get()!=field.getInt(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setField(Object o) {
        imInt.set((Integer) o);
    }
}
