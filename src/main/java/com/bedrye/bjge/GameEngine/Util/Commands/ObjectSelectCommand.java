package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIInspector;
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
        oldSelectedObject = ((BJEUIInspector)EngineManager.getInstance().getActiveScene().findWindowByName("BJEUIInspector")).getObject3DAbstract();
        ((BJEUIInspector)EngineManager.getInstance().getActiveScene().findWindowByName("BJEUIInspector")).setObject3DAbstract(newSelectedObject);
    }

    @Override
    public void undo() {
        ((BJEUIInspector)EngineManager.getInstance().getActiveScene().findWindowByName("BJEUIInspector")).setObject3DAbstract(oldSelectedObject);
    }
}
