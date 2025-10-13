package com.bedrye.bjge.GameEngine.Util.Commands;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

public class ObjectAddScriptCommand implements ICommand {
    private MainBehaviour selectedScript;
    private Object3DAbstract parent;

    public ObjectAddScriptCommand(MainBehaviour selectedScript,Object3DAbstract parent) {
        this.selectedScript = selectedScript;
        this.parent =parent;



    }
    @Override
    public void exec() {
        parent.addScript(selectedScript);
    }

    @Override
    public void undo() {
        parent.removeScript(selectedScript);
    }
}
