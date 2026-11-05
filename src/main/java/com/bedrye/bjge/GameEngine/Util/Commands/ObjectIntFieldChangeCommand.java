package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.lang.reflect.Field;

public class ObjectIntFieldChangeCommand implements ICommand {

    private int oldValue;
    private int newValue;
    private Field field;
    private ImInt imBool;
    private Object o;
    public ObjectIntFieldChangeCommand(Field field, Object o, int newValue, ImInt imBoolean){
        imBool= imBoolean;
        this.field=field;
        try {
            oldValue = (int) this.field.get(o);
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
