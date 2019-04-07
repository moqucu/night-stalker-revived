package org.moqucu.games.nightstalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.Maze;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Log4j2
@Component
public class SplashScreenController implements FxmlController {

    @FXML
    private Button quitButton;

    @FXML
    private Button playButton;

    private final StageManager stageManager;

    private GameLoop gameLoop;

    @Autowired
    @Lazy //lazy since Stage for StageManager not available yet at initialization time
    public SplashScreenController(StageManager stageManager) {

        this.stageManager = stageManager;
    }

    @Override
    public void initialize() {
    }

    @FXML
    public void quitButtonPressed(ActionEvent event) {

        log.debug(event);
        if (gameLoop != null)
            gameLoop.stop();
        System.exit(0);
    }

    @FXML
    public void playButtonPressed(ActionEvent event) {

        log.debug(event);
        Parent parentNode = stageManager.switchScene(FxmlView.GAME_SCREEN);
        Maze maze = (Maze)parentNode
                .getChildrenUnmodifiable()
                .filtered(node -> node instanceof Maze)
                .get(0);

        gameLoop = new GameLoop(maze);
        gameLoop.start();
    }
}
