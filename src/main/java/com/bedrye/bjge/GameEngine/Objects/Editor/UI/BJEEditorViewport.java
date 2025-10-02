package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;

public class BJEEditorViewport  extends BJEUIWindow {





        public void update() {
            ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

            ImVec2 windowSize = getLargestSizeForViewport();
            ImVec2 windowPos = getCenteredPositionForViewport(windowSize);

            ImGui.setCursorPos(windowPos.x, windowPos.y);

            ImVec2 topLeft = new ImVec2();
            ImGui.getCursorScreenPos(topLeft);
            topLeft.x -= ImGui.getScrollX();
            topLeft.y -= ImGui.getScrollY();
            setPosition(topLeft.x, topLeft.y);
            setSize(windowSize.x,windowSize.y);

            int textureId = EngineWindowManager.getInstance().getBjeFrameBuffer().getTextureId();
            ImGui.image(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);






            ImGui.end();
            if (ImGui.beginMainMenuBar()) {
                if (ImGui.beginMenu("File")) {
                    if (ImGui.menuItem("Open", "Ctrl+O")) {
                        EngineWindowManager.getInstance().loadFromFile();
                    }
                    if (ImGui.menuItem("Save", "Ctrl+S")) {
                        EngineWindowManager.getInstance().save();
                    }
                    if (ImGui.menuItem("Save as..")) {
                        EngineWindowManager.getInstance().saveAs();
                    }
                    ImGui.endMenu();
                }

                if (ImGui.beginMenu("Run")) {
                    if (ImGui.menuItem("Editor", "Ctrl+R")) {
                        EngineWindowManager.getInstance().InEngineRun();
                    }
                    if (ImGui.menuItem("Compile", "Ctrl+I")) {
                        EngineWindowManager.getInstance().save();
                    }
                    ImGui.endMenu();
                }
                ImGui.endMainMenuBar();
            }

        }



        private ImVec2 getLargestSizeForViewport() {
            ImVec2 windowSize = new ImVec2();
            ImGui.getContentRegionAvail(windowSize);
            windowSize.x -= ImGui.getScrollX();
            windowSize.y -= ImGui.getScrollY();

            float aspectWidth = windowSize.x;
            float aspectHeight = aspectWidth / EngineWindowManager.getInstance().getTargetAspectRatio();
            if (aspectHeight > windowSize.y) {
                aspectHeight = windowSize.y;
                aspectWidth = aspectHeight * EngineWindowManager.getInstance().getTargetAspectRatio();
            }

            return new ImVec2(aspectWidth, aspectHeight);
        }

        private static ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize) {
            ImVec2 windowSize = new ImVec2();
            ImGui.getContentRegionAvail(windowSize);
            windowSize.x -= ImGui.getScrollX();
            windowSize.y -= ImGui.getScrollY();

            float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
            float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

            return new ImVec2(viewportX + ImGui.getCursorPosX(),
                    viewportY + ImGui.getCursorPosY());
        }

}
