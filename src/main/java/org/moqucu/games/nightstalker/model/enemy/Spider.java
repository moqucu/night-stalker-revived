package org.moqucu.games.nightstalker.model.enemy;

import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

import java.beans.PropertyChangeListener;

public class Spider extends MovableObject {

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
        setFrameRate(5);
        setVelocity(25);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        setImageMapFileName("/images/spider.png");
        setDirection(Direction.Down);
        setLowerAndUpperBoundaryBasedOnDirection();
        setInitialImageIndex(0);
        setXPosition(96);
        setYPosition(32);
        PropertyChangeListener propertyChangeListener = evt -> {

            switch (evt.getPropertyName()) {
                case "YPosition" -> {
                    if (evt.getNewValue().equals(160.0) && getVelocity() < 50) {
                        setMazeAlgorithm(MazeAlgorithm.Random);
                        setVelocity(50.);
                    }
                }
                case "direction" -> setLowerAndUpperBoundaryBasedOnDirection();
            }
        };
        addPropertyChangeListener(propertyChangeListener);
        setAnimated(true);
        setInMotion(true);
    }

    @Override
    public boolean canChangePosition() {

        return true;
    }
}
