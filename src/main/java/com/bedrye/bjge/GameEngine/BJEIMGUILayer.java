package com.bedrye.bjge.GameEngine;
import imgui.*;


import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
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


    }
    public void newFrame(){
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

    }


    /**
     * Method called in the end of the main cycle.
     * It renders ImGui and swaps GLFW buffers to show an updated frame.
     */
    public void endFrame() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }


    }

    /**
     * Method to destroy Dear ImGui context.
     */
    protected void disposeImGui() {
        ImGui.destroyContext();
    }
}
