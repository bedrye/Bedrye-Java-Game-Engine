package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import com.bedrye.bjge.GameEngine.Util.Interfaces.IGameSpace;

public class ObjectChangeParentCommand implements ICommand {

    private Object3DAbstract selectedObject;
    private IGameSpace oldParent;
    private IGameSpace newParent;

    public ObjectChangeParentCommand( IGameSpace parent ,Object3DAbstract selectedObject) {
        this.selectedObject = selectedObject;
        this.oldParent = selectedObject.getParent();
        this.newParent = parent;
    }

    @Override
    public void exec() {
        newParent.addChildObject(selectedObject);
    }

    @Override
    public void undo() {
        oldParent.addChildObject(selectedObject);
    }
}
