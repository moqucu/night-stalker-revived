package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.controller.GameController;

public class NightStalkerRevived extends Application {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private GameController gameController;

    @Override
    public void start(Stage stage) {

        gameController = new GameController(stage);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
