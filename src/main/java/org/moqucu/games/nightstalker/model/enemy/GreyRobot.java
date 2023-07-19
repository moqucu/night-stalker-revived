package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

public class GreyRobot extends MovableObject {

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
        setDirection(Direction.Right);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
        setXPosition(48);
        setYPosition(320);
        setAnimated(true);
        setInMotion(true);
        addPropertyChangeListener(evt -> {

            if (evt.getPropertyName().equals("direction"))
                setVelocity(30.);
        });
    }
}
