package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.lang.reflect.Field;

public class ObjectStringFieldChangeCommand implements ICommand {

    private String oldValue;
    private String newValue;
    private Field field;
    private ImString imBool;
    private Object o;
    public ObjectStringFieldChangeCommand(Field field, Object o, String newValue, ImString imBoolean){
        imBool= imBoolean;
        this.field=field;
        try {
            oldValue = (String) this.field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.newValue = newValue;
        this.o=o;

    }
    @Override
    public void exec() {
        try {
            imBool.set(newValue);
            field.set(o,newValue);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo() {
        try {
            imBool.set(oldValue);
            field.set(o,oldValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
