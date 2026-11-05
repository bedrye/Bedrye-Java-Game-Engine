package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImDouble;
import imgui.type.ImFloat;

import java.lang.reflect.Field;

public class ObjectDoubleFieldChangeCommand implements ICommand {

    private double oldValue;
    private double newValue;
    private Field field;
    private ImDouble imBool;
    private Object o;
    public ObjectDoubleFieldChangeCommand(Field field, Object o, double newValue, ImDouble imBoolean){
        imBool= imBoolean;
        this.field=field;
        try {
            oldValue = (double) this.field.get(o);
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
