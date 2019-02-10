package org.moqucu.games.nightstalker;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

public class StageManager {

    private final Stage primaryStage;

    public StageManager(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }

    private Scene prepareScene(Parent rootNode) {

        Scene scene = Optional.ofNullable(primaryStage.getScene()).orElse(new Scene(rootNode));
        scene.setRoot(rootNode);

        return scene;
    }

    public void switchScene(Parent rootNode, String title) {

        primaryStage.setTitle(title);
        primaryStage.setScene(prepareScene(rootNode));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
