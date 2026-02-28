package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields.BJEUISettableField;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import org.joml.Vector3f;

public class ObjectTransformScaleChangeCommand implements ICommand {

    private Vector3f oldValue;
    private Vector3f newValue;
    private BJEUISettableField settableField;
    private Object3DAbstract o;
    public ObjectTransformScaleChangeCommand(Object3DAbstract o, Vector3f oldValue, Vector3f newValue, BJEUISettableField settableField) {
        this.oldValue = new Vector3f(oldValue);
        this.newValue = new Vector3f(newValue);
        this.o=o;
        this.settableField = settableField;

    }
    @Override
    public void exec() {
        o.setScale(newValue);
    }

    @Override
    public void undo() {
        o.setScale(oldValue);
    }
}
