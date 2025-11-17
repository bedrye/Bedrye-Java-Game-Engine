package com.bedrye.bjge.GameEngine;

import com.bedrye.bjge.GameEngine.Util.Interfaces.ICommand;

import java.util.Stack;

public class BJECommandManager {

    private final Stack<ICommand> undoStack = new Stack<>();
    private final Stack<ICommand> redoStack = new Stack<>();
    private final int capacity;

    public BJECommandManager(int capacity) {
        this.capacity = capacity;
    }

    public void executeCommand(ICommand command) {
        command.exec();
        undoStack.push(command);
        if (undoStack.size() > capacity) {
            undoStack.remove(0);
        }

        redoStack.clear();
    }


    public void undo() {
        if(!EngineManager.getInstance().isInEditMode())return;
        if (undoStack.isEmpty()) return;
        ICommand command = undoStack.pop();
        command.undo();
        redoStack.push(command);

        if (redoStack.size() > capacity) {
            redoStack.remove(0);
        }
    }


    public void redo() {
        if(!EngineManager.getInstance().isInEditMode())return;
        if (redoStack.isEmpty()) return;

        ICommand command = redoStack.pop();
        command.exec();
        undoStack.push(command);

        if (undoStack.size() > capacity) {
            undoStack.remove(0);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();


    }
}
