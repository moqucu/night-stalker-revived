package org.moqucu.games.nightstalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.Maze;
import org.moqucu.games.nightstalker.view.StageManager;

@Data
@Log4j2
public class SplashScreenController {

    @FXML
    private Button quitButton;

    @FXML
    private Button playButton;

    private final StageManager stageManager;

    private GameLoop gameLoop;

    private GameWorld gameWorld = new GameWorld();

    public SplashScreenController(StageManager stageManager) {

        this.stageManager = stageManager;
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
        Maze maze = (Maze) parentNode
                .getChildrenUnmodifiable()
                .filtered(node -> node instanceof Maze)
                .get(0);

        gameLoop = new GameLoop(maze, gameWorld);
        gameLoop.start();
    }
}
