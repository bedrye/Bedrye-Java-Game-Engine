package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class ObjectFloatFieldChangeCommand implements ICommand {

    private float oldValue;
    private float newValue;
    private Field field;
    private ImFloat imBool;
    private Object o;
    public ObjectFloatFieldChangeCommand(Field field, Object o, float newValue, ImFloat imBoolean){
        imBool= imBoolean;
        this.field=field;
        try {
            oldValue = (float) this.field.get(o);
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
