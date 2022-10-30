package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.moqucu.games.nightstalker.controller.GameController;

public class NightStalkerRevived extends Application {

    @Getter
    private GameController gameController;

    @Override
    public void start(Stage stage) {

        gameController = new GameController(stage);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
