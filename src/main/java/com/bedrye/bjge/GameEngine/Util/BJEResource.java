package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIDragSource;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIVisible;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import imgui.ImGui;
import imgui.flag.ImGuiDragDropFlags;

import java.io.*;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // store the concrete class name in JSON
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public abstract class BJEResource implements Serializable, BJEUIVisible, BJEUIDragSource {
    private final String path;
    private final String name;

    public String getName() {
        return name;
    }

    public BJEResource(){
        path="INTERNAL";
        name="INTERNAL";
    }
    @JsonCreator
    public BJEResource(@JsonProperty("path") String path, @JsonProperty("name")String name) {
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
            ImGui.setDragDropPayload(getPayloadName(),this);
            ImGui.text(name);

            ImGui.endDragDropSource();


        }
    }
    @Override
    public void hide() {
        ImGui.popID();
    }

    @Override
    public String getPayloadName(){
        return getClass().getSimpleName();
    }

}
