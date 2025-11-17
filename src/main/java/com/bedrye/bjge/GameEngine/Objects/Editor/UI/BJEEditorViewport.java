package com.bedrye.bjge.GameEngine.Objects.Editor.UI;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.BJETexture;
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

            int textureId = EngineManager.getInstance().getBjeFrameBuffer().getTextureId();
            ImGui.image(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);






            ImGui.end();
            if (ImGui.beginMainMenuBar()) {
                if (ImGui.beginMenu("Project")) {
                    if (ImGui.menuItem("New" )) {
                        EngineManager.getInstance().createWorkSpace();
                    }
                    if (ImGui.menuItem("Load" )) {
                        EngineManager.getInstance().loadWorkSpace();
                    }
                    ImGui.separator();
                    if (ImGui.menuItem("Undo", "CTRL+Z",null, EngineManager.getInstance().getBjeCommandManager().canUndo())) {
                        EngineManager.getInstance().getBjeCommandManager().undo();
                    }
                    if (ImGui.menuItem("Redo","CTRL+Y",null, EngineManager.getInstance().getBjeCommandManager().canRedo())) {
                        EngineManager.getInstance().getBjeCommandManager().redo();
                    }
                    ImGui.endMenu();
                }
                if (ImGui.beginMenu("Scene", EngineManager.getInstance().hasProject())) {
                    if (ImGui.menuItem("Open", "Ctrl+O")) {
                        EngineManager.getInstance().loadSceneFromFile();
                    }
                    if (ImGui.menuItem("Save", "Ctrl+S",null, EngineManager.getInstance().isInSavedScene())) {
                        EngineManager.getInstance().saveScene();
                    }
                    if (ImGui.menuItem("Save as..")) {
                        EngineManager.getInstance().saveSceneAs();
                    }
                    ImGui.endMenu();
                }

                if(EngineManager.getInstance().isInEditMode()) {
                    if (ImGui.menuItem("  Run",null,null, EngineManager.getInstance().hasProject())) {

                        EngineManager.getInstance().InEngineRun();

                    }

                    ImGui.sameLine(ImGui.getCursorPosX() - ImGui.getFontSize() * 2-32);
                    float origin =ImGui.getCursorPosY();
                    ImGui.setCursorPosY(origin + 3);
                    ImGui.image(((BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("runbutton.png")).getTextureID(),12,12);
                    ImGui.setCursorPosY(origin );
                    ImGui.newLine();
                }
                else {
                    if (ImGui.menuItem(" Stop")) {

                        EngineManager.getInstance().InEngineStop();

                    }
                    ImGui.sameLine(ImGui.getCursorPosX() - ImGui.getFontSize() * 2-34);
                    float origin =ImGui.getCursorPosY();
                    ImGui.setCursorPosY(origin + 3);
                    ImGui.image(((BJETexture) EngineManager.getInstance().getBjeResourceManager().getByName("stopbutton.png")).getTextureID(),12,12);
                    ImGui.setCursorPosY(origin );
                    ImGui.newLine();
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
            float aspectHeight = aspectWidth / EngineManager.getInstance().getTargetAspectRatio();
            if (aspectHeight > windowSize.y) {
                aspectHeight = windowSize.y;
                aspectWidth = aspectHeight * EngineManager.getInstance().getTargetAspectRatio();
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
