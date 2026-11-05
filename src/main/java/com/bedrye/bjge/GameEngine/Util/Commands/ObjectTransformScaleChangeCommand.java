package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields.BJEUISettableField;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.type.ImFloat;
import org.joml.Vector3f;

public class ObjectTransformScaleChangeCommand implements ICommand {

    private Vector3f oldValue;
    private Vector3f newValue;
    private BJEUISettableField settableField;
    private ImFloat x;
    private ImFloat y;
    private ImFloat z;
    private Object3DAbstract o;
    public ObjectTransformScaleChangeCommand(Object3DAbstract o, Vector3f oldValue, Vector3f newValue, ImFloat xf, ImFloat yf, ImFloat zf, BJEUISettableField settableField) {
        x =xf;
        y=yf;
        z=zf;
        this.oldValue = new Vector3f(oldValue);
        this.newValue = new Vector3f(newValue);
        this.o=o;
        this.settableField = settableField;

    }
    @Override
    public void exec() {

        x.set(newValue.x);
        y.set(newValue.y);
        z.set(newValue.z);
        o.setScale(newValue);
    }

    @Override
    public void undo() {

        x.set(oldValue.x);
        y.set(oldValue.y);
        z.set(oldValue.z);
        o.setScale(oldValue);
    }
}
