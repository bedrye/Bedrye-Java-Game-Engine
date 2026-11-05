package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImBoolean;

import java.lang.reflect.Field;

public class ObjectBoolFieldChangeCommand implements ICommand {

    private boolean oldValue;
    private boolean newValue;
    private Field field;
    private ImBoolean imBool;
    private Object o;
    public ObjectBoolFieldChangeCommand(Field field, Object o, boolean newValue,ImBoolean imBoolean){
        imBool= imBoolean;
        this.field=field;
        try {
            oldValue = (boolean) this.field.get(o);
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
