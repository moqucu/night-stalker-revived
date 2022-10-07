package org.moqucu.games.nightstalker.model.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

public class BatModel extends MovableObject {

    private double elapsedTimeSinceSpawning = 0;

    @Getter
    private boolean awake;

    public BatModel() {

        super();
        setImageMapFileName("/images/bat.png");
        setLowerAnimationIndex(1);
        setUpperAnimationIndex(5);
        setFrameRate(10);
        setDirection(Direction.Left);
        setVelocity(50);
        setMazeGraphFileName("/maze-graph-enemy.json");
        setMazeAlgorithm(MazeAlgorithm.Random);
    }

    @Override
    public void elapseTime(double milliseconds) {

        super.elapseTime(milliseconds);
        this.elapsedTimeSinceSpawning += milliseconds;

        if (elapsedTimeSinceSpawning > 3 && !awake) {

            setAnimated(true);
            setInMotion(true);
            awake = true;
        }
    }
}
