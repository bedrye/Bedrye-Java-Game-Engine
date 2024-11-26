package com.bedrye.bjge;

import com.almasb.fxgl.core.math.Vec3;
import com.bedrye.Objects.GameScene;
import com.bedrye.Objects.Object3DAbstract;
import com.bedrye.Objects.Object3D;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    final Object3DAbstract gamescene = new GameScene();
    final Group root = new Group();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        root.getChildren().add(new PerspectiveCamera(true));
        gamescene.addChild(new Object3D(new Box(),new PhongMaterial()));
        gamescene.initialize();
        stage.setTitle("Molecule Sample Application");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}