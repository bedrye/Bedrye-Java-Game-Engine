package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

import java.lang.reflect.Field;

public class ObjectFieldChangeCommand<T> implements ICommand {

    private T oldValue;
    private T newValue;
    private Field field;
    private Object o;
    public ObjectFieldChangeCommand(Field field, Object o, T newValue){
        this.field=field;
        try {
            oldValue = (T) this.field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.newValue = newValue;
        this.o=o;

    }
    @Override
    public void exec() {
        try {
            field.set(o,newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo() {
        try {
            field.set(o,oldValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
