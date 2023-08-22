package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;

public class Bat extends MovableObject implements Resettable {

    private double elapsedTimeSinceSpawning = 0;

    @Getter
    private boolean awake;

    @Getter
    private double sleepTime;

    private Double initialXPosition;

    private Double initialYPosition;

    private Integer initialImageIndex;

    private Direction initialDirection;

    public Bat() {

        super();
        setImageMapFileName("/images/bat.png");
        setLowerAnimationIndex(1);
        setUpperAnimationIndex(5);
        setFrameRate(10);
        setVelocity(50);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
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

    @Override
    public boolean canChangePosition() {

        return true;
    }

    @Override
    public void reset() {

        setAnimated(false);
        setInMotion(false);
        if (initialXPosition != null)
            setXPosition(initialXPosition);
        if (initialYPosition != null)
            setYPosition(initialYPosition);
        if (initialImageIndex != null)
            setInitialImageIndex(initialImageIndex);
        if (initialDirection != null)
                setDirection(initialDirection);
        awake = false;
        elapsedTimeSinceSpawning = 0;
    }

    @Override
    public void setXPosition(double xPosition) {

        if (initialXPosition == null)
            initialXPosition = xPosition;
        super.setXPosition(xPosition);
    }

    @Override
    public void setYPosition(double yPosition) {

        if (initialYPosition == null)
            initialYPosition = yPosition;
        super.setYPosition(yPosition);
    }

    @Override
    public void setInitialImageIndex(int initialImageIndex) {

        if (this.initialImageIndex == null)
            this.initialImageIndex = initialImageIndex;
        super.setInitialImageIndex(initialImageIndex);
    }

    @Override
    public void setDirection(Direction direction) {

        if (initialDirection == null)
            initialDirection = direction;
        super.setDirection(direction);
    }
}
