module com.bedrye.multiplayershooter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.bedrye.bjge to javafx.fxml;
    exports com.bedrye.bjge;
    exports com.bedrye.Objects;
    opens com.bedrye.Objects to javafx.fxml;
}