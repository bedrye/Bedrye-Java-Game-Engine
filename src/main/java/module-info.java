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


    opens com.bedrye.bjge to javafx.fxml;
    exports com.bedrye.bjge;
    exports com.bedrye.Objects;
    opens com.bedrye.Objects to javafx.fxml;
    exports com.bedrye.bjge.GameEngine;
    opens com.bedrye.bjge.GameEngine to javafx.fxml;
    opens com.bedrye.bjge.GameEngine.Util to javafx.fxml;
    exports com.bedrye.bjge.GameEngine.Util;
    exports com.bedrye.bjge.GameEngine.Objects.Editor.UI;
    opens com.bedrye.bjge.GameEngine.Objects.Editor.UI to javafx.fxml;
    exports com.bedrye.bjge.GameEngine.Objects;
    opens com.bedrye.bjge.GameEngine.Objects to javafx.fxml;
}