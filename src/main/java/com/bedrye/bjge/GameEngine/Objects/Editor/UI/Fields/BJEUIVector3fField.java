package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

import java.lang.reflect.Field;

public class BJEUIVector3fField implements BJEUIField,BJEUISettableField {
    String prefix;
    ImFloat imFloatx,imFloaty,imFloatz;
    Field field;
    Object o;
    public BJEUIVector3fField(Field field, Object o) throws IllegalAccessException {
        this.field= field;
        this.o = o;
        prefix=field.getType().getSimpleName() + " " + field.getName();
        Vector3f vector3f = (Vector3f) field.get(o);
        imFloatx = new ImFloat(vector3f.x);
        imFloaty = new ImFloat(vector3f.y);
        imFloatz = new ImFloat(vector3f.z);


    }
    @Override
    public void showField() {
        ImGui.pushID(field.getName());
        ImGui.text(prefix);
        ImGui.inputFloat("x", imFloatx);
        ImGui.inputFloat("y", imFloaty);
        ImGui.inputFloat("z", imFloatz);
        ImGui.popID();
        if(isChanged()){
            Vector3f newVec = new Vector3f( imFloatx.get(),imFloaty.get(),imFloatz.get()) ;
            //EngineManager.getInstance().getBjeCommandManager().executeCommand(  new ObjectFieldChangeCommand<>(field,o,newVec,this));
        }
    }


    public boolean isChanged() {
        try {
            Vector3f vector3f = (Vector3f) field.get(o);
            return (vector3f.x !=  imFloatx.get()||vector3f.y!= imFloaty.get()||vector3f.z!= imFloatz.get());

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setField(Object o) {
        org.joml.Vector3f vector3f1 = (Vector3f) o;
        imFloatx.set(vector3f1.x);
        imFloaty.set(vector3f1.y);
        imFloatz.set(vector3f1.z);
    }
}
