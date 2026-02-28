package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;

import java.lang.reflect.Field;

public class BJEUIResourceField implements BJEUIField,BJEUISettableField {


    String prefix;
    Field field;
    Object o;

    public BJEUIResourceField(Field field, Object o) {
        this.field = field;
        this.o = o;
        prefix = field.getType().getSimpleName() + " " + field.getName();


    }


    @Override
    public void showField() {

        try {
            if (field.get(o) == null)
                ImGui.selectable(field.getName(), 50, 50);
            else {
                ((BJEResource) field.get(o)).show();
                ((BJEResource) field.get(o)).hide();

            }

        if (ImGui.beginDragDropTarget()) {
            if (field.get(o) == null)
                acceptPayload(field, o, field.getType().getSimpleName());
            else
                acceptPayload(field, o, ((BJEResource) field.get(o)).getPayloadName());


            ImGui.endDragDropTarget();


        }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void acceptPayload(Field field,Object o,String name) {

        BJEResource payload = ImGui.acceptDragDropPayload(name);

        if (payload != null) {

            //EngineManager.getInstance().getBjeCommandManager().executeCommand(new ObjectFieldChangeCommand<>(field,o,payload,this));
        }

    }

    @Override
    public void setField(Object o) {

    }
}
