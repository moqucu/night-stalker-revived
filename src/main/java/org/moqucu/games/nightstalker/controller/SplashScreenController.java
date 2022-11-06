package org.moqucu.games.nightstalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.utility.FxmlView;

@Log4j2
@SuppressWarnings("unused")
public class SplashScreenController {

    @FXML
    private Button quitButton;

    @FXML
    private Button playButton;

    private final GameController gameController;

    public SplashScreenController(GameController gameController) {

        this.gameController = gameController;
    }

    @FXML
    public void quitButtonPressed(ActionEvent event) {

        log.debug(event);
        gameController.stopGameLoop();
        gameController.endGame();
    }

    @FXML
    public void playButtonPressed(ActionEvent event) {

        log.debug(event);
        gameController.switchScene(FxmlView.GAME_SCREEN);
        gameController.startGameLoop();
    }
}
