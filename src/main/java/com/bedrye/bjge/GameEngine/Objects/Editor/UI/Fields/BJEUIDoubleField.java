package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImDouble;
import imgui.type.ImFloat;

import java.lang.reflect.Field;

public class BJEUIDoubleField implements BJEUIField,BJEUISettableField {
    String prefix;
    ImDouble imDouble;
    Field field;
    Object o;
    public BJEUIDoubleField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix = field.getType().getSimpleName() + " " + field.getName();
        imDouble= new ImDouble(field.getDouble(o));


    }
    @Override
    public void showField() {
        ImGui.inputDouble(prefix, imDouble);
        if(isChanged()){
           // EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectFieldChangeCommand<>(field, o, imDouble.get(),this::setField));
        }
    }


    public boolean isChanged() {
        try {
            return imDouble.get()!=field.getDouble(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setField(Object o) {
        imDouble.set((Double) o);
    }
}
