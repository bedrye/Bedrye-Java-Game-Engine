module com.bedrye.multiplayershooter {


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


    opens com.bedrye.bjge.GameEngine.Util to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Scripts to com.fasterxml.jackson.databind;
    opens com.bedrye.Objects to com.fasterxml.jackson.databind;
    opens com.bedrye.bjge.GameEngine.Objects to com.fasterxml.jackson.databind;
    exports com.bedrye.bjge;
    exports com.bedrye.Objects;
    exports com.bedrye.bjge.GameEngine;
    exports com.bedrye.bjge.GameEngine.Util;
    exports com.bedrye.bjge.GameEngine.Objects.Editor.UI;
    opens com.bedrye.bjge.GameEngine.Objects.Editor.UI to javafx.fxml;

}