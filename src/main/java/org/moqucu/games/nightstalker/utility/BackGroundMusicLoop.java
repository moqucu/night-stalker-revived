package org.moqucu.games.nightstalker.utility;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import org.moqucu.games.nightstalker.view.Maze;

import java.util.Objects;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class BackGroundMusicLoop extends Task<Void> {

    private final AudioClip audio
            = new AudioClip(Objects.requireNonNull(Maze.class.getResource("/sounds/background.wav")).toString());

    @Override
    protected Void call() {

        audio.setVolume(0.5f);
        audio.setCycleCount(INDEFINITE);
        audio.play();

        return null;
    }

    @Override
    protected void cancelled() {

        audio.stop();
    }

    @Override
    public boolean cancel(boolean b) {

        return super.cancel(b);
    }
}
