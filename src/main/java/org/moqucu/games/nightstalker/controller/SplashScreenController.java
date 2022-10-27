package org.moqucu.games.nightstalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.FxmlView;

@Data
@Log4j2
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
        System.exit(0);
    }

    @FXML
    public void playButtonPressed(ActionEvent event) {

        log.debug(event);
        gameController.switchScene(FxmlView.GAME_SCREEN);
        gameController.startGameLoop();
    }
}
