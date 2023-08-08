package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;

public class GreyRobot extends MovableObject implements Resettable {

    @Getter
    private boolean active = false;

    @Getter
    private boolean slow = true;

    @Getter
    private boolean fallingApart = true;

    public GreyRobot() {

        super();
        setImageMapFileName("/images/grey-robot.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(10);
        setVelocity(15);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
        reset();
        addPropertyChangeListener(evt -> {

            if (evt.getPropertyName().equals("direction"))
                setVelocity(30.);
        });
    }

    @Override
    public void reset() {

        setXPosition(48);
        setYPosition(320);
        setDirection(Direction.Right);
        setAnimated(true);
        setInMotion(true);
    }
}
