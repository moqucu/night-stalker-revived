package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.object.Bullet;

import java.beans.PropertyChangeListener;

public class Spider extends MovableObject implements Resettable {

    private static final double RESPAWN_TIME_MS = 5000;

    @Getter
    private boolean slow = true;

    private boolean dead = false;

    private double timeSinceDeath = 0;

    private final PropertyChangeListener propertyChangeListener = evt -> {

        switch (evt.getPropertyName()) {
            case "YPosition" -> {
                if (slow && (Double)evt.getNewValue() >= 160.0) {
                    setMazeAlgorithm(MazeAlgorithm.Random);
                    setVelocity(50.);
                    slow = false;
                }
            }
            case "direction" -> setLowerAndUpperBoundaryBasedOnDirection();
        }
    };

    private void setLowerAndUpperBoundaryBasedOnDirection() {

        switch (getDirection()) {
            case Left, Right -> {
                setLowerAnimationIndex(2);
                setUpperAnimationIndex(3);
            }
            default -> {
                setLowerAnimationIndex(0);
                setUpperAnimationIndex(1);
            }
        }
     }

    public Spider() {

        super();

        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setImageMapFileName("/images/spider.png");
        setBoundingBoxDimensions(0, 4, 32, 22);
        reset();
        setAnimated(true);
        setInMotion(true);
    }

    @Override
    public boolean canChangePosition() {

        return true;
    }

    @Override
    public void elapseTime(double milliseconds) {

        if (dead) {
            timeSinceDeath += milliseconds;
            if (timeSinceDeath >= RESPAWN_TIME_MS)
                reset();
        } else {
            super.elapseTime(milliseconds);
        }
    }

    @Override
    public void collisionOccurredWith(Collidable anotherCollidable) {

        if (anotherCollidable instanceof Bullet && isObjectVisible()) {
            setInMotion(false);
            setAnimated(false);
            setObjectVisible(false);
            dead = true;
            timeSinceDeath = 0;
        }
    }

    @Override
    public void reset() {

        slow = true;
        dead = false;
        timeSinceDeath = 0;
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        removePropertyChangeListener(propertyChangeListener);
        setFrameRate(5);
        setVelocity(25);
        setDirection(Direction.Down);
        setLowerAndUpperBoundaryBasedOnDirection();
        setInitialImageIndex(0);
        setXPosition(96);
        setYPosition(32);
        setObjectVisible(true);
        setAnimated(true);
        setInMotion(true);
        addPropertyChangeListener(propertyChangeListener);
    }
}
