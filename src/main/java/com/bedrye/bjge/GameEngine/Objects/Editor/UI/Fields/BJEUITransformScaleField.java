package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformRotationChangeCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformScaleChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

public class BJEUITransformScaleField extends  BJEUITransformVectorField{
    public BJEUITransformScaleField(Object3DAbstract object3DAbstract, String prefix)  {
        super(object3DAbstract, prefix);
        vector3f =object.getScale();
        imFloatx = new ImFloat(vector3f.x);
        imFloaty = new ImFloat(vector3f.y);
        imFloatz = new ImFloat(vector3f.z);

    }
    @Override
    public void showField() {
        ImGui.pushID(prefix);
        ImGui.text(prefix);
        ImGui.inputFloat("Xs", imFloatx);
        ImGui.inputFloat("Ys", imFloaty);
        ImGui.inputFloat("Zs", imFloatz);
        ImGui.popID();
        vector3f = object.getScale();
        if(isChanged()){
            Vector3f newVec = new Vector3f( imFloatx.get(),imFloaty.get(),imFloatz.get()) ;
            EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectTransformScaleChangeCommand(object,vector3f,newVec,this) );
        }
    }
}
