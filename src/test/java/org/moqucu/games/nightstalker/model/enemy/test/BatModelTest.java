package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.enemy.BatModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BatModelTest {

    private final BatModel batModel = new BatModel();

    @Test
    public void hasAwakeProperty() {

        assertThat(batModel, hasProperty("awake"));
    }

    @Test
    public void awakeOfTypeBoolen() {

        assertThat(batModel.isAwake(), isA(Boolean.class));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(batModel.getImageMapFileName(), is("/images/bat.png"));
    }

    @Test
    public void lowerAnimationIndexIsOne() {

        assertThat(batModel.getLowerAnimationIndex(), is(1));
    }

    @Test
    public void upperAnimationIndexIsFive() {

        assertThat(batModel.getUpperAnimationIndex(), is(5));
    }

    @Test
    public void frameRateIsTen() {

        assertThat(batModel.getFrameRate(), is(10));
    }

    @Test
    public void velocityIsFifty() {

        assertThat(batModel.getVelocity(), is(50.));
    }

    @Test
    public void pointsToEnemyMazeGraph() {

        assertThat(batModel.getMazeGraphFileName(), is("/maze-graph-enemy.json"));
    }

    @Test
    public void randomAlgorithm() {

        assertThat(batModel.getMazeAlgorithm(), is(MazeAlgorithm.Random));
    }

    @Test
    public void beforeThreeSecondsBatIsNotAwake() {

        final GameWorld gameWorld = new GameWorld();
        final BatModel anotherBatModel = new BatModel();
        gameWorld.add(anotherBatModel);
        gameWorld.pulse(2999);

        assertThat(anotherBatModel.isAwake(), is(false));
        assertThat(anotherBatModel.isInMotion(), is(false));
        assertThat(anotherBatModel.isAnimated(), is(false));
    }

    @Test
    public void atThreeSecondsBatIsAwake() {

        final GameWorld gameWorld = new GameWorld();
        final BatModel anotherBatModel = new BatModel();
        anotherBatModel.setXPosition(528.0);
        anotherBatModel.setYPosition(96.0);
        gameWorld.add(anotherBatModel);
        gameWorld.pulse(3000);

        assertThat(anotherBatModel.isAwake(), is(true));
        assertThat(anotherBatModel.isInMotion(), is(true));
        assertThat(anotherBatModel.isAnimated(), is(true));
    }
}
