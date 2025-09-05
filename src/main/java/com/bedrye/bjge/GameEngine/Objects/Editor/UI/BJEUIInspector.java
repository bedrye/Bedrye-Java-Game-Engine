package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
import imgui.ImGui;
import imgui.type.*;
import org.joml.Vector3f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class BJEUIInspector extends BJEUIWindow  {
    int boolcount = 0;
    int intcount = 0;
    int floatcount = 0;
    int doublecount = 0;
    int stringcount = 0;

    public ArrayList<ImBoolean> booleans = new ArrayList<>();
    public ArrayList<ImInt> ints = new ArrayList<>();
    public ArrayList<ImFloat> floats = new ArrayList<>();
    public ArrayList<ImDouble> doubles = new ArrayList<>();
    public ArrayList<ImString> strings = new ArrayList<>();
    private Object3DAbstract object3DAbstract;

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

                   inspectObject(object3DAbstract);



            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ImGui.end();


        }
    }
    public void inspectObject(Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getFields();
        ImGui.pushID(o.getClass().getSimpleName());
        for (Field field : fields) {

            if (MainBehaviour.class.isAssignableFrom(field.getType())) {

                inspectObject(field.get(o));
            }
            else if (BJEResource.class.isAssignableFrom(field.getType())) {

                if (field.get(o) == null)
                    ImGui.selectable(field.getName(), 50, 50);
                else {
                    ((BJEResource) field.get(o)).show();
                    ((BJEResource) field.get(o)).hide();
                    //ImGui.selectable(field.getName(), 50, 50);
                }
                if (ImGui.beginDragDropTarget()) {
                    BJEResource payload = ImGui.acceptDragDropPayload("Resource");

                    if (payload != null) {
                        //((BJETexture) payload).initialize();
                        field.set(o, payload);
                    }



                    ImGui.endDragDropTarget();
                }

            }

            else
                fieldCheck(field,o);
                }
        Field[] fieldinvs = o.getClass().getDeclaredFields();

        for (Field field : fieldinvs) {
            if (!Modifier.isPublic(field.getModifiers())&&field.isAnnotationPresent(InspectorVisible.class)) {

                field.setAccessible(true);
                if (field.getType().isAnnotationPresent(InspectorVisible.class)) {
                    inspectObject(field.get(o));

                }

                else

                    fieldCheck(field, o);
            }
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
                field.setBoolean(object, booleans.get(boolcount - 1).get());

                break;

            case "int":
                intcount++;
                if (intcount > ints.size())
                    ints.add(new ImInt(field.getInt(object)));

                ImGui.inputInt(field.getType().getSimpleName() + " " + field.getName(), ints.get(intcount - 1));
                field.setInt(object, ints.get(intcount - 1).get());

                break;

            case "float":
                floatcount++;
                if (floatcount > floats.size())
                    floats.add(new ImFloat(field.getFloat(object)));

                ImGui.inputFloat(field.getType().getSimpleName() + " " + field.getName(), floats.get(floatcount - 1));
                field.setFloat(object, floats.get(floatcount - 1).get());

                break;
            case "double":
                doublecount++;
                if (doublecount > doubles.size())
                    doubles.add(new ImDouble(field.getDouble(object)));

                ImGui.inputDouble(field.getType().getSimpleName() + " " + field.getName(), doubles.get(doublecount - 1));
                field.setDouble(object, doubles.get(doublecount - 1).get());

                break;
            case "String":
                stringcount++;
                if (stringcount > strings.size())
                    strings.add(new ImString((String) field.get(object)));

                ImGui.inputText(field.getType().getSimpleName() + " " + field.getName(), strings.get(stringcount - 1));
                field.set(object, strings.get(stringcount - 1).get());

                break;
            case "Vector3f":
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
                    vector3f = new Vector3f(floats.get(floatcount - 3).get(),floats.get(floatcount - 2).get(),floats.get(floatcount - 1).get());
                    field.set(object, vector3f);

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
        ImGui.text("Scripts");
        int removeAt=0;
        boolean remove =false;
        for (MainBehaviour main: object.getScriptList()
             ) {
            try {
                ImGui.pushID(main.getClass().getSimpleName());
                ImGui.separator();
                ImGui.text(main.getClass().getSimpleName());
                inspectObject(main);
                if(ImGui.button("-")) {
                    remove = true;
                    break;
                }
                removeAt++;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            finally {
                ImGui.popID();

            }
        }
        if(remove)
            object.removeScript(removeAt);


    }
}
