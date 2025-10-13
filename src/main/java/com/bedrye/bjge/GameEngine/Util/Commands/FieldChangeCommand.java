package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

import java.lang.reflect.Field;

public class FieldChangeCommand<T> implements ICommand {

    private T oldValue;
    private T newValue;
    private Field field;
    public FieldChangeCommand(Field field,Object o,T newValue){
        this.field=field;
        try {
            oldValue = (T) this.field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.newValue = newValue;


    }
    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
