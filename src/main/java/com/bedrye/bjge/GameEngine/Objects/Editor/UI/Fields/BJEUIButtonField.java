package com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;
import imgui.ImGui;

public class BJEUIButtonField implements BJEUIField{
    String content;
    ICommand command;
    public BJEUIButtonField(ICommand command, String content){
        this.content = content;
        this.command = command;
    }
    @Override
    public void showField() {
        if(ImGui.button(content)) {
            EngineManager.getInstance().getBjeCommandManager().executeCommand(command);
        }
    }
}
