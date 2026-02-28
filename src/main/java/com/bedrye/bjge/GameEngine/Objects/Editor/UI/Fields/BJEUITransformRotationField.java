package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformPositionChangeCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformRotationChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

public class BJEUITransformRotationField extends  BJEUITransformVectorField{
    public BJEUITransformRotationField(Object3DAbstract object3DAbstract, String prefix)  {
        super(object3DAbstract, prefix);
        imFloatx = new ImFloat(object.getRotationX());
        imFloaty = new ImFloat(object.getRotationY());
        imFloatz = new ImFloat(object.getRotationZ());

    }
    @Override
    public void showField() {
        ImGui.pushID(prefix);
        ImGui.text(prefix);
        ImGui.inputFloat("Xr", imFloatx);
        ImGui.inputFloat("Yr", imFloaty);
        ImGui.inputFloat("Zr", imFloatz);
        ImGui.popID();
        vector3f = object.getRotation();
        if(isChanged()){
            Vector3f newVec = new Vector3f( imFloatx.get(),imFloaty.get(),imFloatz.get()) ;
            EngineManager.getInstance().getBjeCommandManager().executeCommand( new ObjectTransformRotationChangeCommand(object,vector3f,newVec,this) );
        }
    }
}
