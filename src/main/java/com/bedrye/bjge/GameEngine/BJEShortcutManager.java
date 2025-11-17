package com.bedrye.bjge.GameEngine;

import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectDeleteCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectSelectCommand;
import com.bedrye.bjge.GameEngine.Util.Input.BJEKeyInput;
import com.bedrye.bjge.GameEngine.Util.Input.KeyInputType;

import static org.lwjgl.glfw.GLFW.*;

public class BJEShortcutManager {

    public BJEShortcutManager(){}

    public void onKeyPress(BJEKeyInput input){
        if(input.keyInputType== KeyInputType.Press) {
            if ((input.keyId == GLFW_KEY_Z && KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT_CONTROL)) ||
                    (input.keyId == GLFW_KEY_LEFT_CONTROL && KeyListener.getInstance().isKeyPressed(GLFW_KEY_Z)))
                EngineManager.getInstance().getBjeCommandManager().undo();

            else if ((input.keyId == GLFW_KEY_Y && KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT_CONTROL)) ||
                    (input.keyId == GLFW_KEY_LEFT_CONTROL && KeyListener.getInstance().isKeyPressed(GLFW_KEY_Y)))
                EngineManager.getInstance().getBjeCommandManager().redo();
            else if (input.keyId == GLFW_KEY_DELETE)
                EngineManager.getInstance().getBjeCommandManager().executeCommand(new ObjectDeleteCommand(
                        ((BJEEditorScene) EngineManager.getInstance().getActiveScene()).inspector.getObject3DAbstract()
                ));
            else if (input.keyId == GLFW_KEY_ESCAPE)
                EngineManager.getInstance().getBjeCommandManager().executeCommand(new ObjectSelectCommand(
                        null
                ));
        }
    }

}
