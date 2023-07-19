package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GreyRobotTest {

    private final GreyRobot greyRobot = new GreyRobot();

    @Test
    public void isOfTypeMovableObject() {

        assertThat(greyRobot, isA(MovableObject.class));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(greyRobot.getImageMapFileName(), is("/images/grey-robot.png"));
    }

    @Test
    public void lowerAnimationIndexIsZero() {

        assertThat(greyRobot.getLowerAnimationIndex(), is(0));
    }

    @Test
    public void upperAnimationIndexIsOne() {

        assertThat(greyRobot.getUpperAnimationIndex(), is(1));
    }

    @Test
    public void frameRateIsTen() {

        assertThat(greyRobot.getFrameRate(), is(10));
    }

    @Test
    public void initialVelocityIsFifteen() {

        final GameWorld gameWorld = new GameWorld();
        final GreyRobot anotherGreyRobotModel = new GreyRobot();
        gameWorld.add(anotherGreyRobotModel);

        assertThat(anotherGreyRobotModel.getVelocity(), is(15.));
    }

    @Test
    public void whenChangingDirectionForTheFirstTimeVelocityDoubles() {

        final GameWorld gameWorld = new GameWorld();
        final GreyRobot anotherGreyRobotModel = new GreyRobot();
        gameWorld.add(anotherGreyRobotModel);

        anotherGreyRobotModel.setDirection(Direction.Left);
        assertThat(anotherGreyRobotModel.getVelocity(), is(30.));
    }

    @Test
    public void initialDirectionIsRight() {

        assertThat(greyRobot.getDirection(), is(Direction.Right));
    }

    @Test
    public void initialXPositionIsFortyEight() {

        assertThat(greyRobot.getXPosition(), is(48.));
    }

    @Test
    public void initialYPositionIsThreeHundredTwenty() {

        assertThat(greyRobot.getYPosition(), is(320.));
    }

    @Test
    public void initiallyInMotion() {

        assertThat(greyRobot.isInMotion(), is(true));
    }

    @Test
    public void initiallyAnimated() {

        assertThat(greyRobot.isAnimated(), is(true));
    }

    @Test
    public void pointsToEnemyMazeGraph() {

        assertThat(greyRobot.getMazeGraphFileName(), is("/json/maze-graph-enemy.json"));
    }

    @Test
    public void randomAlgorithm() {

        assertThat(greyRobot.getMazeAlgorithm(), is(MazeAlgorithm.Random));
    }

    @Test
    public void hasActiveProperty() {

        assertThat(greyRobot, hasProperty("active"));
    }

    @Test
    public void activePropertyOfTypeBoolean() {

        assertThat(greyRobot.isActive(), isA(Boolean.class));
    }

    @Test
    public void hasSlowProperty() {

        assertThat(greyRobot, hasProperty("slow"));
    }

    @Test
    public void slowPropertyOfTypeBoolean() {

        assertThat(greyRobot.isSlow(), isA(Boolean.class));
    }

    @Test
    public void hasFallingApartProperty() {

        assertThat(greyRobot, hasProperty("fallingApart"));
    }

    @Test
    public void fallingApartPropertyOfTypeBoolean() {

        assertThat(greyRobot.isFallingApart(), isA(Boolean.class));
    }
}
