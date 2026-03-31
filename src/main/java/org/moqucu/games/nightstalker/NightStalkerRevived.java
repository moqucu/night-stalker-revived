package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.utility.GameLoop;
import org.moqucu.games.nightstalker.utility.SystemWrapper;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.Objects;

@Getter
public class NightStalkerRevived extends Application {

    private GameController gameController;

    @SneakyThrows
    @Override
    public void start(Stage stage) {

        URL url = Objects.requireNonNull(getClass().getResource("/sounds/background.wav"));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        gameController = new GameController(
                stage,
                new SystemWrapper(),
                new BackGroundMusicLoop(clip),
                new GameLoop()
        );
    }

    public static void main(String[] args) {

        launch(args);
    }
}
