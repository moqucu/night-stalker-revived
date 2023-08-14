package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;

import java.beans.PropertyChangeListener;

public class Spider extends MovableObject implements Resettable {

    @Getter
    private boolean slow = true;

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
        reset();
        setAnimated(true);
        setInMotion(true);
    }

    @Override
    public boolean canChangePosition() {

        return true;
    }

    @Override
    public void reset() {

        slow = true;
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        removePropertyChangeListener(propertyChangeListener);
        setFrameRate(5);
        setVelocity(25);
        setDirection(Direction.Down);
        setLowerAndUpperBoundaryBasedOnDirection();
        setInitialImageIndex(0);
        setXPosition(96);
        setYPosition(32);
        addPropertyChangeListener(propertyChangeListener);
    }
}
