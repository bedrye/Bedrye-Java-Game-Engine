package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields.BJEUIField;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
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

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;

public class BJEUIInspector extends BJEUIWindow  {
    int boolcount = 0;
    int intcount = 0;
    int floatcount = 0;
    int doublecount = 0;
    int stringcount = 0;
    private ArrayList<BJEUIField> fields = new ArrayList<>();
    private ArrayList<ImBoolean> booleans = new ArrayList<>();
    private ArrayList<ImInt> ints = new ArrayList<>();
    private ArrayList<ImFloat> floats = new ArrayList<>();
    private ArrayList<ImDouble> doubles = new ArrayList<>();
    private ArrayList<ImString> strings = new ArrayList<>();
    private Object3DAbstract object3DAbstract;
    private ICommand command;

    public Object3DAbstract getObject3DAbstract() {
        return object3DAbstract;
    }

    public void setObject3DAbstract(Object3DAbstract object3DAbstract) {
        this.object3DAbstract = object3DAbstract;
        booleans.clear();
        ints.clear();
        floats.clear();
        boolcount = 0;
        intcount = 0;
        floatcount = 0;
        doublecount = 0;
        stringcount = 0;

    }


    public void update() {
        if(object3DAbstract !=null) {
            ImGui.begin("Inspector");
            try {

                inspectTransform(object3DAbstract);

                openPopup();



            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ImGui.end();

            if(command!=null) {
                EngineWindowManager.getInstance().getBjeCommandManager().executeCommand(command);
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

                    acceptPayload(field,o,((BJEResource) field.get(o)).getPayloadName());


                    ImGui.endDragDropTarget();
                }



            } else
                    fieldCheck(field, o);
            }



        ImGui.popID();

    }



    public void fieldCheck(Field field,Object object) throws IllegalAccessException {


        switch (field.getType().getSimpleName()) {
            case "boolean":
                boolcount++;
                if (boolcount > booleans.size())
                    booleans.add(new ImBoolean(field.getBoolean(object)));

                ImGui.checkbox(field.getType().getSimpleName() + " " + field.getName(), booleans.get(boolcount - 1));
                if(field.getBoolean(object)!= booleans.get(boolcount - 1).get() ){
                    command = new ObjectFieldChangeCommand<>(field, object, booleans.get(boolcount - 1).get());
                }

                break;

            case "int":
                intcount++;
                if (intcount > ints.size())
                    ints.add(new ImInt(field.getInt(object)));

                ImGui.inputInt(field.getType().getSimpleName() + " " + field.getName(), ints.get(intcount - 1));
                if(field.getInt(object)!= ints.get(intcount - 1).get() ){
                    command = new ObjectFieldChangeCommand<>(field, object, ints.get(intcount - 1).get());
                }

                break;

            case "float":
                floatcount++;
                if (floatcount > floats.size())
                    floats.add(new ImFloat(field.getFloat(object)));

                ImGui.inputFloat(field.getType().getSimpleName() + " " + field.getName(), floats.get(floatcount - 1));
                if(field.getFloat(object)!= floats.get(floatcount - 1).get() ){
                    command = new ObjectFieldChangeCommand<>(field,object,floats.get(floatcount - 1).get());
                }
                //field.setFloat(object, floats.get(floatcount - 1).get());

                break;
            case "double":
                doublecount++;
                if (doublecount > doubles.size())
                    doubles.add(new ImDouble(field.getDouble(object)));

                ImGui.inputDouble(field.getType().getSimpleName() + " " + field.getName(), doubles.get(doublecount - 1));
                if(field.getDouble(object)!= doubles.get(doublecount - 1).get() ){
                    command = new ObjectFieldChangeCommand<>(field, object, doubles.get(doublecount - 1).get());
                }

                break;
            case "String":
                stringcount++;
                if (stringcount > strings.size())
                    strings.add(new ImString((String) field.get(object)));

                ImGui.inputText(field.getType().getSimpleName() + " " + field.getName(), strings.get(stringcount - 1));
                if(!field.get(object).equals(strings.get(stringcount - 1).get()) ){
                    command = new ObjectFieldChangeCommand<>(field, object, strings.get(stringcount - 1).get());
                }
                break;
            case "Vector3f":
                ImGui.pushID(field.getName());
                ImGui.text(field.getType().getSimpleName() + " " + field.getName());
                Vector3f vector3f = (Vector3f) field.get(object);
                    floatcount+=3;
                    if (floatcount > floats.size()) {
                        floats.add(new ImFloat(vector3f.x));
                        floats.add(new ImFloat(vector3f.y));
                        floats.add(new ImFloat(vector3f.z));
                    }
                    ImGui.inputFloat("x", floats.get(floatcount - 3));
                    ImGui.inputFloat("y", floats.get(floatcount - 2));
                    ImGui.inputFloat("z", floats.get(floatcount - 1));
                    if(vector3f.x != floats.get(floatcount - 3).get()||vector3f.y!=floats.get(floatcount - 2).get()||vector3f.z!=floats.get(floatcount - 1).get()){
                        Vector3f newVec = new Vector3f( floats.get(floatcount - 3).get(),floats.get(floatcount - 2).get(),floats.get(floatcount - 1).get()) ;
                        command = new ObjectFieldChangeCommand<>(field,object,newVec);
                    }
                ImGui.popID();

            break;
            default:
                ImGui.text(field.getType().getSimpleName() + " " + field.getName());
                break;
        }
    }
    public void inspectTransform(Object3DAbstract object){
        ImGui.text("Position");

        floatcount+=3;
        if (floatcount > floats.size()) {
            floats.add(new ImFloat(object.getLocalPosX()));
            floats.add(new ImFloat(object.getLocalPosY()));
            floats.add(new ImFloat(object.getLocalPosZ()));
        }
        ImGui.inputFloat("Xp", floats.get(floatcount - 3));
        ImGui.inputFloat("Yp", floats.get(floatcount - 2));
        ImGui.inputFloat("Zp", floats.get(floatcount - 1));
        object.setLocalPosX(floats.get(floatcount - 3).get());
        object.setLocalPosY(floats.get(floatcount - 2).get());
        object.setLocalPosZ(floats.get(floatcount - 1).get());

        ImGui.text("Rotation");

        floatcount+=3;
        if (floatcount > floats.size()) {
            floats.add(new ImFloat(object.getRotationX()));
            floats.add(new ImFloat(object.getRotationY()));
            floats.add(new ImFloat(object.getRotationZ()));
        }
        ImGui.inputFloat("Xr", floats.get(floatcount - 3));
        ImGui.inputFloat("Yr", floats.get(floatcount - 2));
        ImGui.inputFloat("Zr", floats.get(floatcount - 1));
        object.setRotationX(floats.get(floatcount - 3).get());
        object.setRotationY(floats.get(floatcount - 2).get());
        object.setRotationZ(floats.get(floatcount - 1).get());

        ImGui.text("Scale");

        floatcount+=3;
        if (floatcount > floats.size()) {
            floats.add(new ImFloat(object.getScale().x));
            floats.add(new ImFloat(object.getScale().y));
            floats.add(new ImFloat(object.getScale().z));
        }
        ImGui.inputFloat("Xs", floats.get(floatcount - 3));
        ImGui.inputFloat("Ys", floats.get(floatcount - 2));
        ImGui.inputFloat("Zs", floats.get(floatcount - 1));
        object.setScale(new Vector3f(floats.get(floatcount - 3).get(),floats.get(floatcount - 2).get(),floats.get(floatcount - 1).get()));
        try {
            inspectObject(object3DAbstract);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ImGui.text("Scripts");
        Iterator<MainBehaviour> i = object.getScriptList().iterator();
        int count = 0;
        while (i.hasNext())
             {
                 MainBehaviour main = i.next();
            try {
                ImGui.pushID(main.getClass().getSimpleName()+count);
                count++;
                ImGui.separator();
                ImGui.text(main.getClass().getSimpleName());
                inspectObject(main);

                if(ImGui.button("-")) {
                    command = new ObjectRemoveScriptCommand(main,object);
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            finally {
                ImGui.popID();

            }
        }



    }

    private void openPopup(){
        if (ImGui.button("+",40,20)){

            ImGui.openPopup("addPopup");


        }
        if (ImGui.beginPopup("addPopup")){
            for (MainBehaviour beh: EngineWindowManager.getInstance().getBjeResourceManager().getScripts().values()
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

