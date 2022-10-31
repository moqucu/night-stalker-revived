package org.moqucu.games.nightstalker.utility;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class BackGroundMusicLoop extends Task<Void> {

    private final AudioClip audioClip;

    public BackGroundMusicLoop(AudioClip audioClip) {

        this.audioClip = audioClip;
    }
    @Override
    protected Void call() {

        audioClip.setVolume(0.5f);
        audioClip.setCycleCount(INDEFINITE);
        audioClip.play();

        return null;
    }

    public void publicCall() {

        call();
    }

    @Override
    protected void cancelled() {

        audioClip.stop();
    }

    @Override
    public boolean cancel(boolean b) {

        if (b) {
            audioClip.stop();
            return true;
        }
        else
            return false;
    }
}
