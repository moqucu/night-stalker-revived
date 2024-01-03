package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;

@Getter
public class GreyRobot extends MovableObject implements Resettable {

    private final boolean active = false;

    private boolean slow = true;

    private final boolean fallingApart = true;

    public GreyRobot() {

        super();
        setImageMapFileName("/images/grey-robot.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(10);
        setMazeGraphFileName("/json/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
        reset();
        addPropertyChangeListener(evt -> {

            if (slow && evt.getPropertyName().equals("XPosition") && (Double)evt.getNewValue() >= 96.0) {

                setVelocity(30);
                slow = false;
            }
        });
    }

    @Override
    public void reset() {

        slow = true;
        setDirection(Direction.Right);
        setVelocity(15);
        setXPosition(48);
        setYPosition(320);
        setAnimated(true);
        setInMotion(true);
    }
}
