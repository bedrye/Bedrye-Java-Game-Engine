package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.Objects.GameScene;
import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.BJEFolder;
import com.bedrye.bjge.GameEngine.Util.BJEResource;
import imgui.ImGui;

import java.util.ArrayList;

public class BJEAssetHierarchy extends BJEUIWindow {
    public BJEFolder getSelectedFolder() {
        return SelectedFolder;
    }

    public void setSelectedFolder(BJEFolder selectedFolder) {
        SelectedFolder = selectedFolder;
    }

    private BJEFolder SelectedFolder = new BJEFolder("O:\\GIt\\MultiplayerShooter\\Assets","Assets");

    public BJEAssetHierarchy(){
        SelectedFolder.init(null);
    }
    public void goBack(){
        if(SelectedFolder.Parent!=null)
            SelectedFolder= SelectedFolder.Parent;


    }

    @Override
    public void update() {
        ImGui.begin("Assets");
        ImGui.selectable("..");
        if (ImGui.isItemClicked())
        {
            goBack();
        }
        SelectedFolder.showContents();
        ImGui.end();

    }
}
