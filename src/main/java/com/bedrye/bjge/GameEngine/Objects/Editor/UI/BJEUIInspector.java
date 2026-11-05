package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields.*;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectFieldChangeCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectAddScriptCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectRemoveScriptCommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.ImGui;
import imgui.type.*;
import org.joml.Vector3f;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class BJEUIInspector extends BJEUIWindow  {

    private ArrayList<BJEUIField> fields = new ArrayList<>();
    private Object3DAbstract object3DAbstract;
    private ICommand command;

    public Object3DAbstract getObject3DAbstract() {
        return object3DAbstract;
    }

    public void setObject3DAbstract(Object3DAbstract object3DAbstract) {
        this.object3DAbstract = object3DAbstract;
        fields.clear();
        if (object3DAbstract!=null) {
            inspectTransformation(object3DAbstract);
        }

    }


    public void update() {
        if(object3DAbstract !=null) {
            ImGui.begin("Inspector");
            try {


                showFields();

                openPopup();



            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ImGui.end();

            if(command!=null) {
                EngineManager.getInstance().getBjeCommandManager().executeCommand(command);
                command = null;
            }
            }

        }

        private List<Field> joinArrays(Field[] f1,Field[] fp2){


            List<Field> n = new ArrayList<>();
            for (Field field: fp2) {
                if (!Modifier.isPublic(field.getModifiers())
                        && field.isAnnotationPresent(InspectorVisible.class)) {
                        field.setAccessible(true);
                        n.add(field);
                    }

            }
            Collections.addAll(n,f1);
            return n;
        }
        public void initialInspection(Object o){
            try {
                List<Field> fields = joinArrays(o.getClass().getFields(), o.getClass().getDeclaredFields());
                //inspectTransformation(object3DAbstract);
                for (Field field : fields) {
                    fieldCheck(field, o);

                }
            }
             catch (Exception e) {
            throw new RuntimeException(e);
            }


        }
        public void showFields(){
            ImGui.pushID(object3DAbstract.getClass().getSimpleName());
            for (BJEUIField field : fields) {
            field.showField();
            }
            ImGui.popID();


        }
    public void inspectObject(Object o) throws IllegalAccessException {
        List<Field> fields = joinArrays(o.getClass().getFields(), o.getClass().getDeclaredFields());


        ImGui.pushID(o.getClass().getSimpleName());
        for (Field field : fields) {

            if (MainBehaviour.class.isAssignableFrom(field.getType())) {
                    inspectObject(field.get(o));
            } else if (BJEResource.class.isAssignableFrom(field.getType())) {
                if (field.get(o) == null)
                    ImGui.selectable(field.getName(), 50, 50);
                else {
                    ((BJEResource) field.get(o)).show();
                    ((BJEResource) field.get(o)).hide();

                }
                if (ImGui.beginDragDropTarget()) {
                    if (field.get(o)==null)
                        acceptPayload(field,o,field.getType().getSimpleName());
                    else
                        acceptPayload(field,o,((BJEResource) field.get(o)).getPayloadName());


                    ImGui.endDragDropTarget();
                }



            } else
                    fieldCheck(field, o);
            }



        ImGui.popID();

    }



    public void fieldCheck(Field field,Object object) throws IllegalAccessException {

        if (BJEResource.class.isAssignableFrom(field.getType())){
            fields.add(new BJEUIResourceField(field,object));
            return;
        }
        switch (field.getType().getSimpleName()) {
            case "boolean":
                fields.add(new BJEUIBoolField(field,object));
                break;

            case "int":
                fields.add(new BJEUIIntField(field,object));

                break;

            case "float":
                fields.add(new BJEUIFloatField(field,object));

                break;
            case "double":
                fields.add(new BJEUIDoubleField(field,object));

                break;
            case "String":
                fields.add(new BJEUIStringField(field,object));
                break;
            case "Vector3f":
                fields.add(new BJEUIVector3fField(field,object));

            break;
            default:
                ImGui.text(field.getType().getSimpleName() + " " + field.getName());
                break;
        }
    }
    public void inspectTransformation(Object3DAbstract object){

        fields.add(new BJEUITransformPositionField(object,"Position"));
        fields.add(new BJEUITransformRotationField(object,"Rotation"));
        fields.add(new BJEUITransformScaleField(object,"Scale"));
        initialInspection(object3DAbstract);

        fields.add(new BJEUITextField("Scripts"));
        Iterator<MainBehaviour> i = object.getScriptList().iterator();
        int count = 0;
        while (i.hasNext())
        {
             MainBehaviour main = i.next();

                fields.add(new BJEUIPush(count+""));
                count++;
                fields.add(new BJEUISeparatorField());
                fields.add(new BJEUITextField(main.getClass().getSimpleName()));
                initialInspection(main);
                fields.add(new BJEUIButtonField(new ObjectRemoveScriptCommand(main,object),"-"));
                fields.add(new BJEUIPop());


        }

    }


    private void openPopup(){
        if (ImGui.button("+",40,20)){

            ImGui.openPopup("addPopup");


        }
        if (ImGui.beginPopup("addPopup")){
            for (MainBehaviour beh: EngineManager.getInstance().getBjeResourceManager().getScripts().values()
            ) {
                if (ImGui.button(beh.getClass().getSimpleName())) {
                    try {
                        command = new ObjectAddScriptCommand(beh.getClass().getConstructor().newInstance(),getObject3DAbstract());

                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            ImGui.endPopup();
        }
    }

    private void acceptPayload(Field field,Object o,String name) {

        BJEResource payload = ImGui.acceptDragDropPayload(name);

        if (payload != null) {

            command = new ObjectFieldChangeCommand<>(field,o,payload);
        }

    }


}

