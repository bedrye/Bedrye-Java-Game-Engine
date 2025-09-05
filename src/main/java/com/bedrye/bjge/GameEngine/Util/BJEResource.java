package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIDragSource;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIVisible;
import imgui.ImGui;
import imgui.flag.ImGuiDragDropFlags;

import java.io.*;

public abstract class BJEResource implements Serializable, BJEUIVisible, BJEUIDragSource {
    protected final String path;
    private final String name;

    public String getName() {
        return name;
    }

    public BJEResource(String path, String name) {
        this.path = path;
        this.name = name;
    }


    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "BJEResource{" +
                "path='" + path + '\'' +
                '}';
    }
    public final void Save() throws IOException {

        FileOutputStream fos =  new FileOutputStream(getPath()+".bjer");


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        fos.close();
    }

    @Override
    public void show() {
        ImGui.pushID(path);
        ImGui.selectable(name);



    }

    @Override
    public void DragStart() {
        if (ImGui.beginDragDropSource())
        {
            ImGui.setDragDropPayload("Resource",this);
            ImGui.text(name);

            ImGui.endDragDropSource();


        }
    }
    @Override
    public void hide() {
        ImGui.popID();
    }

}
