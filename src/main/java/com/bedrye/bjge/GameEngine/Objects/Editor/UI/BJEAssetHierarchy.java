package com.bedrye.bjge.GameEngine.Objects.Editor.UI;



import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Objects.Editor.BJEFolder;
import imgui.ImGui;


public class BJEAssetHierarchy extends BJEUIWindow {


    public void goBack(){
        BJEFolder selectedFolder = EngineManager.getInstance().getBjeResourceManager().getCurrentPath();
        if(selectedFolder.getParent() !=null)
            EngineManager.getInstance().getBjeResourceManager().setCurrentPath(selectedFolder.getParent());


    }

    @Override
    public void update() {
        ImGui.begin("Assets");
        ImGui.selectable("..");
        if (ImGui.isItemClicked())
        {
            goBack();
        }
        EngineManager.getInstance().getBjeResourceManager().getCurrentPath().showContents();
        ImGui.end();

    }
}
