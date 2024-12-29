module com.bedrye.multiplayershooter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires org.joml;
    requires org.joml.primitives;
    requires org.lwjgl.stb;


    opens com.bedrye.bjge to javafx.fxml;
    exports com.bedrye.bjge;
    exports com.bedrye.Objects;
    opens com.bedrye.Objects to javafx.fxml;
    exports com.bedrye.bjge.GameEngine;
    opens com.bedrye.bjge.GameEngine to javafx.fxml;
}