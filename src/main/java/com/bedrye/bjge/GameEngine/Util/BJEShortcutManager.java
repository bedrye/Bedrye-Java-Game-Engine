package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Listeners.KeyListener;
import com.bedrye.bjge.GameEngine.Objects.BJEEditorScene;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectDeleteCommand;
import com.bedrye.bjge.GameEngine.Util.Commands.ObjectSelectCommand;
import com.bedrye.bjge.GameEngine.Util.Input.BJEKeyInput;
import com.bedrye.bjge.GameEngine.Util.Input.KeyInputType;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public class BJEShortcutManager {

    public BJEShortcutManager(){}

    public void onKeyPress(BJEKeyInput input){
        if(input.keyInputType== KeyInputType.Press) {
            if ((input.keyId == GLFW_KEY_Z && KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT_CONTROL)) ||
                    (input.keyId == GLFW_KEY_LEFT_CONTROL && KeyListener.getInstance().isKeyPressed(GLFW_KEY_Z)))
                EngineWindowManager.getInstance().getBjeCommandManager().undo();

            else if ((input.keyId == GLFW_KEY_Y && KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT_CONTROL)) ||
                    (input.keyId == GLFW_KEY_LEFT_CONTROL && KeyListener.getInstance().isKeyPressed(GLFW_KEY_Y)))
                EngineWindowManager.getInstance().getBjeCommandManager().redo();
            else if (input.keyId == GLFW_KEY_DELETE)
                EngineWindowManager.getInstance().getBjeCommandManager().executeCommand(new ObjectDeleteCommand(
                        ((BJEEditorScene) EngineWindowManager.getInstance().getActiveScene()).inspector.getObject3DAbstract()
                ));
            else if (input.keyId == GLFW_KEY_ESCAPE)
                EngineWindowManager.getInstance().getBjeCommandManager().executeCommand(new ObjectSelectCommand(
                        null
                ));
        }
    }

}
