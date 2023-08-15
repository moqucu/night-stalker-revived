package org.moqucu.games.nightstalker.model.hero;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

public class NightStalker extends MovableObject {

    @Getter
    private boolean running;

    public NightStalker() {

        setXPosition(288);
        setYPosition(144);
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        setMazeGraphFileName("/json/maze-graph-night-stalker.json");
        setImageMapFileName("/images/night-stalker.png");
        setFrameRate(10);
        setVelocity(30);
        setInitialImageIndex(0);
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(0);
        setDirection(Direction.Up);
    }

    public void setRunning(boolean running) {

        final boolean oldRunning = this.running;
        this.running = running;
        propertyChangeSupport.firePropertyChange(
                "running",
                oldRunning,
                running
        );
    }

    public void stop() {

        setRunning(false);
        setAnimated(false);
        setInMotion(false);
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(0);
    }
}
