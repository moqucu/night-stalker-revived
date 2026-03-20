package org.moqucu.games.nightstalker.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import org.moqucu.games.nightstalker.utility.FxmlView;

public class LoadingScreenController {

    @FXML
    private ProgressBar loadingBar;

    private final GameController gameController;

    public LoadingScreenController(GameController gameController) {

        this.gameController = gameController;
    }

    @FXML
    public void initialize() {

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), new KeyValue(loadingBar.progressProperty(), 1.0))
        );
        timeline.setOnFinished(event -> transitionToSplashScreen());
        timeline.play();
    }

    public void transitionToSplashScreen() {

        gameController.switchScene(FxmlView.SPLASH_SCREEN);
    }
}
