package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.object.Bullet;

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
        this.propertyChangeSupport.firePropertyChange(PropertyNames.SLEEP_TIME, oldSleepTime, sleepTime);
    }

    @Override
    public boolean canChangePosition() {

        return true;
    }

    @Override
    public void collisionOccurredWith(Collidable anotherCollidable) {

        if (anotherCollidable instanceof Bullet && isObjectVisible()) {
            setInMotion(false);
            setAnimated(false);
            setObjectVisible(false);
        }
    }

    public void setSpawnXPosition(double x) {

        this.initialXPosition = x;
        super.setXPosition(x);
    }

    public void setSpawnYPosition(double y) {

        this.initialYPosition = y;
        super.setYPosition(y);
    }

    public void setSpawnImageIndex(int imageIndex) {

        this.initialImageIndex = imageIndex;
        super.setInitialImageIndex(imageIndex);
    }

    public void setSpawnDirection(Direction direction) {

        this.initialDirection = direction;
        super.setDirection(direction);
    }

    @Override
    public void reset() {

        setAnimated(false);
        setInMotion(false);
        if (initialXPosition != null)
            super.setXPosition(initialXPosition);
        if (initialYPosition != null)
            super.setYPosition(initialYPosition);
        if (initialImageIndex != null)
            super.setInitialImageIndex(initialImageIndex);
        if (initialDirection != null)
            super.setDirection(initialDirection);
        awake = false;
        elapsedTimeSinceSpawning = 0;
    }
}
