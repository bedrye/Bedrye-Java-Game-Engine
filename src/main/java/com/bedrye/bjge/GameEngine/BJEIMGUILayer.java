package com.bedrye.bjge.GameEngine;
import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEEditorViewport;
import imgui.*;


import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import org.lwjgl.glfw.GLFW;


public class BJEIMGUILayer {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private long windowPtr;

    public BJEIMGUILayer(long windowPtr){
    this.windowPtr=windowPtr;
    }

    public void init(){

        ImGui.createContext();

        imGuiGlfw.init(windowPtr, true);
        imGuiGl3.init("#version 330");
        ImGui.getIO().setConfigFlags(ImGuiConfigFlags.DockingEnable);


    }
    public void newFrame(){
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

    }


    public void endFrame() {
        ImGui.end();
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }


    }

    public void setupDockspace() {
        int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;

        ImGui.setNextWindowPos(0.0f, 0.0f, ImGuiCond.Always);
        ImGui.setNextWindowSize(EngineWindowManager.getInstance().getWidth(), EngineWindowManager.getInstance().getHeight()-80);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
        windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin("Dockspace", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        ImGui.dockSpace(ImGui.getID("Dockspace"));

    }
    protected void disposeImGui() {
        ImGui.destroyContext();
    }
}
