package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformPositionChangeCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectTransformRotationChangeCommand;
import imgui.ImGui;
import imgui.type.ImFloat;
import org.joml.Vector3f;

public class BJEUITransformPositionField extends  BJEUITransformVectorField{
    public BJEUITransformPositionField(Object3DAbstract object3DAbstract, String prefix) {
        super(object3DAbstract, prefix);
        imFloatx = new ImFloat(object.getLocalPosX());
        imFloaty = new ImFloat(object.getLocalPosY());
        imFloatz = new ImFloat(object.getLocalPosZ());

    }
    @Override
    public void showField() {
        ImGui.pushID(prefix);
        ImGui.text(prefix);
        ImGui.inputFloat("Xp", imFloatx);
        ImGui.inputFloat("Yp", imFloaty);
        ImGui.inputFloat("Zp", imFloatz);
        ImGui.popID();
        vector3f = object.getPosition();
        if(isChanged()){
            Vector3f newVec = new Vector3f( imFloatx.get(),imFloaty.get(),imFloatz.get()) ;
            EngineManager.getInstance().getBjeCommandManager().executeCommand(  new ObjectTransformPositionChangeCommand(object,vector3f,newVec,this) );
        }
    }
}
