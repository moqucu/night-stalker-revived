package org.moqucu.games.nightstalker.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.FxmlView;

@Log4j2
public class GameScreenController {

    private final GameController gameController;

    public GameScreenController(GameController gameController) {

        this.gameController = gameController;
    }

    @FXML
    public void onMouseClicked(MouseEvent event) {

        log.debug(event);
        gameController.switchScene(FxmlView.SPLASH_SCREEN);
    }
}
