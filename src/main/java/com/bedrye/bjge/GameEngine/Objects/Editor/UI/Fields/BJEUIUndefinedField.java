package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

import java.lang.reflect.Field;

public class BJEUIUndefinedField implements BJEUIField {
    String prefix;
    ImFloat imFloatx,imFloaty,imFloatz;
    Field field;
    Object o;
    public BJEUIUndefinedField(Field field, Object o)  {
        this.field= field;
        this.o = o;
        prefix=field.getType().getSimpleName() + " " + field.getName();


    }
    @Override
    public void showField() {
        ImGui.text(prefix);
    }

}
