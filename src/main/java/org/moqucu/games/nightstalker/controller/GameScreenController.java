package org.moqucu.games.nightstalker.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.StageManager;

@Data
@Log4j2
public class GameScreenController implements FxmlController {

    private final StageManager stageManager;

    public GameScreenController(StageManager stageManager) {

        this.stageManager = stageManager;
    }

    @Override
    public void initialize() {
    }

    @FXML
    public void onMouseClicked(MouseEvent event) {

        log.debug(event);
        stageManager.switchScene(FxmlView.SPLASH_SCREEN);
    }
}
