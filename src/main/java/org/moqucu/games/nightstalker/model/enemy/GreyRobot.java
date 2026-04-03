package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.object.Bullet;

@Getter
public class GreyRobot extends MovableObject implements Resettable {

    private static final double RESPAWN_TIME_MS = 5000;

    private final boolean active = false;

    private boolean slow = true;

    private final boolean fallingApart = true;

    private boolean dead = false;

    private double timeSinceDeath = 0;

    public GreyRobot() {

        super();
        setImageMapFileName("/images/grey-robot.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(10);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
        setBoundingBoxDimensions(2, 2, 28, 28);
        reset();
        addPropertyChangeListener(evt -> {

            if (slow && evt.getPropertyName().equals(PropertyNames.X_POSITION) && (Double)evt.getNewValue() >= 96.0) {

                setVelocity(30);
                slow = false;
            }
        });
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
        setDirection(Direction.Right);
        setVelocity(15);
        setXPosition(48);
        setYPosition(320);
        setObjectVisible(true);
        setAnimated(true);
        setInMotion(true);
    }
}
