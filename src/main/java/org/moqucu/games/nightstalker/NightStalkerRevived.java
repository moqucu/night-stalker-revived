package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import lombok.Getter;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.utility.GameLoop;
import org.moqucu.games.nightstalker.utility.SystemWrapper;

import java.util.Objects;

@Getter
public class NightStalkerRevived extends Application {

    private GameController gameController;

    @Override
    public void start(Stage stage) {

        gameController = new GameController(
                stage,
                new SystemWrapper(),
                new BackGroundMusicLoop(
                        new AudioClip(
                                Objects.requireNonNull(getClass().getResource("/sounds/background.wav")).toString()
                        )
                ),
                new GameLoop()
        );
    }

    public static void main(String[] args) {

        launch(args);
    }
}
