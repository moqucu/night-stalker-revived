package org.moqucu.games.nightstalker.utility;

import javafx.concurrent.Task;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BackGroundMusicLoop extends Task<Void> {

    private final Clip clip;

    public BackGroundMusicLoop(Clip clip) {

        this.clip = clip;
    }

    @Override
    protected Void call() {

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-6.0f);
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        return null;
    }

    public void publicCall() {

        call();
    }

    @Override
    protected void cancelled() {

        clip.stop();
    }

    @Override
    public boolean cancel(boolean b) {

        if (b) {
            clip.stop();
            return true;
        }
        else
            return false;
    }
}
