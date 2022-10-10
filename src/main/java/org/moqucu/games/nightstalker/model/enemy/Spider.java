package org.moqucu.games.nightstalker.model.enemy;

import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

import java.beans.PropertyChangeListener;

public class Spider extends MovableObject {

    private void setLowerAndUpperBoundaryBasedOnDirection() {

        switch (getDirection()) {
            case Left:
            case Right:
                setLowerAnimationIndex(2);
                setUpperAnimationIndex(3);
                break;
            default:
                setLowerAnimationIndex(0);
                setUpperAnimationIndex(1);
        }
        if (!isAnimated() || !isInMotion()) {

            setAnimated(true);
            setInMotion(true);
        }
    }

    public Spider() {

        super();
        setFrameRate(5);
        setVelocity(25);
        setMazeGraphFileName("/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
        setImageMapFileName("/images/spider.png");
        PropertyChangeListener propertyChangeListener = evt -> {

            if (evt.getPropertyName().equals("direction"))
                setLowerAndUpperBoundaryBasedOnDirection();
        };
        addPropertyChangeListener(propertyChangeListener);
    }
}
