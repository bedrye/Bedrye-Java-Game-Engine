package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

public class ObjectSelectCommand implements ICommand {
    private Object3DAbstract newSelectedObject;
    private Object3DAbstract oldSelectedObject;

    public ObjectSelectCommand(Object3DAbstract newSelectedObject) {
        this.newSelectedObject = newSelectedObject;

    }

    @Override
    public void exec() {
        oldSelectedObject =((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.getObject3DAbstract();
        ((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.setObject3DAbstract(newSelectedObject);
    }

    @Override
    public void undo() {
        ((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.setObject3DAbstract(oldSelectedObject);
    }
}
