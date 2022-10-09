package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

public class Bat extends MovableObject {

    private double elapsedTimeSinceSpawning = 0;

    @Getter
    private boolean awake;

    @Getter
    private double sleepTime;

    public Bat() {

        super();
        setImageMapFileName("/images/bat.png");
        setLowerAnimationIndex(1);
        setUpperAnimationIndex(5);
        setFrameRate(10);
        setDirection(Direction.Left);
        setVelocity(50);
        setMazeGraphFileName("/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
    }

    @Override
    public void elapseTime(double milliseconds) {

        super.elapseTime(milliseconds);
        this.elapsedTimeSinceSpawning += milliseconds;

        if (elapsedTimeSinceSpawning >= sleepTime && !awake) {

            setAnimated(true);
            setInMotion(true);
            awake = true;
        }
    }

    public void setSleepTime(double sleepTime) {

        final double oldSleepTime = this.sleepTime;
        this.sleepTime = sleepTime;
        this.propertyChangeSupport.firePropertyChange("sleepTime", oldSleepTime, sleepTime);
    }
}
