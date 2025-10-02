package com.bedrye.bjge.GameEngine.Objects.Editor.UI;



import com.bedrye.bjge.GameEngine.Objects.Editor.BJEFolder;
import imgui.ImGui;


public class BJEAssetHierarchy extends BJEUIWindow {
    public BJEFolder getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(BJEFolder selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    private BJEFolder selectedFolder = new BJEFolder("O:\\GIt\\MultiplayerShooter\\Assets","Assets");

    public BJEAssetHierarchy(){
        selectedFolder.init(null);
    }
    public void goBack(){
        if(selectedFolder.getParent() !=null)
            selectedFolder = selectedFolder.getParent();


    }

    @Override
    public void update() {
        ImGui.begin("Assets");
        ImGui.selectable("..");
        if (ImGui.isItemClicked())
        {
            goBack();
        }
        selectedFolder.showContents();
        ImGui.end();

    }
}
