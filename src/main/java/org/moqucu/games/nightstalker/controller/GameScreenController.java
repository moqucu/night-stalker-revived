package org.moqucu.games.nightstalker.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Log4j2
@Component
public class GameScreenController implements FxmlController {

    private final StageManager stageManager;

    @Autowired
    @Lazy //lazy since Stage for StageManager not available yet at initialization time
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
