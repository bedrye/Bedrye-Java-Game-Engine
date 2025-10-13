package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

public class ObjectDeleteCommand implements ICommand {

    private Object3DAbstract selectedObject;
    private Object3DAbstract parent;

    public ObjectDeleteCommand(Object3DAbstract selectedObject) {
        this.selectedObject = selectedObject;
        this.parent = selectedObject.getParent();


    }

    @Override
    public void exec() {

        parent.removeChild(selectedObject);
    }

    @Override
    public void undo() {
        parent.addChild(selectedObject);
    }
}

