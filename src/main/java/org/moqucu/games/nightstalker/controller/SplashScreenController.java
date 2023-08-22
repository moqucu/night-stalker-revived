package org.moqucu.games.nightstalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
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
        final Scene gameScreen = gameController.switchScene(FxmlView.GAME_SCREEN);
        gameScreen.setOnKeyPressed(
                keyEvent -> {

                    switch (keyEvent.getCode()) {
                        case S -> gameController.stopGameLoop();
                        case P -> gameController.startGameLoop();
                        case R -> gameController.resetGameWorld();
                        case Q -> gameController.endGame();
                        case UP -> gameController.runNightStalkerWith(Direction.Up);
                        case DOWN -> gameController.runNightStalkerWith(Direction.Down);
                        case LEFT -> gameController.runNightStalkerWith(Direction.Left);
                        case RIGHT -> gameController.runNightStalkerWith(Direction.Right);
                    }
                }
        );
        gameScreen.setOnKeyReleased(
                keyReleasedEvent -> {

                    switch (keyReleasedEvent.getCode()) {
                        case UP, DOWN, LEFT, RIGHT -> gameController.stopNightStalker();
                    }

                }
        );
        gameController.startGameLoop();
    }
}
