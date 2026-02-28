package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

import java.lang.reflect.Field;

public abstract class BJEUITransformVectorField implements BJEUIField,BJEUISettableField{
    String prefix;
    ImFloat imFloatx,imFloaty,imFloatz;
    Object3DAbstract object;
    Vector3f vector3f;
    public BJEUITransformVectorField(Object3DAbstract object3DAbstract,String prefix) {
        object = object3DAbstract;
        this.prefix = prefix;

    }


    public boolean isChanged() {
            return (vector3f.x !=  imFloatx.get()||vector3f.y!= imFloaty.get()||vector3f.z!= imFloatz.get());

    }

    @Override
    public void setField(Object o) {
        org.joml.Vector3f vector3f1 = (Vector3f) o;
        imFloatx.set(vector3f1.x);
        imFloaty.set(vector3f1.y);
        imFloatz.set(vector3f1.z);
    }
}
