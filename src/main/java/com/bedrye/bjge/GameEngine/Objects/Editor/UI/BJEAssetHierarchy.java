package com.bedrye.bjge.GameEngine.Objects.Editor.UI;



import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.GameEngineMain;
import com.bedrye.bjge.GameEngine.Objects.Editor.BJEFolder;
import imgui.ImGui;


public class BJEAssetHierarchy extends BJEUIWindow {


    public void goBack(){
        BJEFolder selectedFolder = EngineWindowManager.getInstance().getBjeResourceManager().getCurrentPath();
        if(selectedFolder.getParent() !=null)
            EngineWindowManager.getInstance().getBjeResourceManager().setCurrentPath(selectedFolder.getParent());


    }

    @Override
    public void update() {
        ImGui.begin("Assets");
        ImGui.selectable("..");
        if (ImGui.isItemClicked())
        {
            goBack();
        }
        EngineWindowManager.getInstance().getBjeResourceManager().getCurrentPath().showContents();
        ImGui.end();

    }
}
