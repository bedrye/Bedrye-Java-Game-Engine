package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

public class ObjectRemoveScriptCommand implements ICommand {
    private MainBehaviour selectedScript;
    private Object3DAbstract parent;

    public ObjectRemoveScriptCommand(MainBehaviour selectedScript, Object3DAbstract parent) {
        this.selectedScript = selectedScript;
        this.parent =parent;



    }
    @Override
    public void undo() {
        parent.addScript(selectedScript);
    }

    @Override
    public void exec() {
        parent.removeScript(selectedScript);
    }
}
