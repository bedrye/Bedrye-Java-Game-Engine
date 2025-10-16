module com.bedrye.bjge {


    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires org.joml;
    requires org.joml.primitives;
    requires org.lwjgl.stb;
    requires org.lwjgl.assimp;
    requires imgui.lwjgl3;
    requires imgui.binding;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires java.compiler;
    requires java.desktop;

    opens com.bedrye.bjge to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Util to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Scripts to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Objects to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Objects.Editor.Prefabs to com.fasterxml.jackson.databind;
    exports com.bedrye.bjge.GameEngine.Objects;
    exports com.bedrye.bjge.GameEngine;
    exports com.bedrye.bjge.GameEngine.Util;
    exports com.bedrye.bjge.GameEngine.Objects.Editor.UI;
    exports com.bedrye.bjge.GameEngine.Objects.Editor.UI.Fields;


}