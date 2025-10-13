package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;

public class ObjectCreateCommand implements ICommand {

    private Object3DAbstract selectedObject;
    private IGameSpace parent;

    public ObjectCreateCommand( IGameSpace parent ,Object3DAbstract selectedObject) {
        this.selectedObject = selectedObject;
        this.parent = parent;


    }

    @Override
    public void exec() {
        parent.addChildObject(selectedObject);
    }

    @Override
    public void undo() {
        parent.removeChildObject(selectedObject);
    }
}

