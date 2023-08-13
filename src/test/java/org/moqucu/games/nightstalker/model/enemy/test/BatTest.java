package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.Resettable;
import org.moqucu.games.nightstalker.model.enemy.Bat;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BatTest {

        /*

            setAnimationProperties(
                Map.of
                        (
                                States.Asleep, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.Awake, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.Moving, AnimationProperty.builder()
                                        .autoReversible(true)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(1).upper(5).build())
                                        .build(),
                                States.Hit, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(6).upper(9).build())
                                        .build()
                        )
        );

     */

    private final Bat bat = new Bat();

    private final double sleepTime;

    public BatTest() {

        Random random = new Random();
        sleepTime = random.doubles(2999.0, 3001.0).findAny().orElseThrow();
    }

    @Test
    public void batOfTypeResettable() {

        assertThat(bat, isA(Resettable.class));
    }

    @Test
    public void resettingTheBatSetsEverythingBack() {

        final Bat localBat = new Bat();
        localBat.setSleepTime(100);
        localBat.setDirection(Direction.Left);
        localBat.setXPosition(528.0);
        localBat.setYPosition(96);
        localBat.setInitialImageIndex(0);
        assertThat(localBat.isAwake(), is(false));
        assertThat(localBat.isInMotion(), is(false));
        assertThat(localBat.isAnimated(), is(false));

        localBat.elapseTime(101);
        assertThat(localBat.isAwake(), is(true));
        assertThat(localBat.isInMotion(), is(true));
        assertThat(localBat.isAnimated(), is(true));

        localBat.reset();

        assertThat(localBat.isAwake(), is(false));
        assertThat(localBat.isInMotion(), is(false));
        assertThat(localBat.isAnimated(), is(false));
        assertThat(localBat.getDirection(), is (Direction.Left));
        assertThat(localBat.getXPosition(), is (528.0));
        assertThat(localBat.getYPosition(), is (96.0));
        assertThat(localBat.getImageIndex(), is (0));
    }

    @Test
    public void hasAwakeProperty() {

        assertThat(bat, hasProperty("awake"));
    }

    @Test
    public void awakeOfTypeBoolean() {

        assertThat(bat.isAwake(), isA(Boolean.class));
    }

    @Test
    public void hasSleepTimeProperty() {

        assertThat(bat, hasProperty("sleepTime"));
    }

    @Test
    public void sleepTimeOfTypeDouble() {

        assertThat(bat.getSleepTime(), isA(Double.class));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(bat.getImageMapFileName(), is("/images/bat.png"));
    }

    @Test
    public void lowerAnimationIndexIsOne() {

        assertThat(bat.getLowerAnimationIndex(), is(1));
    }

    @Test
    public void upperAnimationIndexIsFive() {

        assertThat(bat.getUpperAnimationIndex(), is(5));
    }

    @Test
    public void frameRateIsTen() {

        assertThat(bat.getFrameRate(), is(10));
    }

    @Test
    public void velocityIsFifty() {

        assertThat(bat.getVelocity(), is(50.));
    }

    @Test
    public void pointsToEnemyMazeGraph() {

        assertThat(bat.getMazeGraphFileName(), is("/json/maze-graph-enemy.json"));
    }

    @Test
    public void randomAlgorithm() {

        assertThat(bat.getMazeAlgorithm(), is(MazeAlgorithm.Random));
    }

    @Test
    public void beforeSleepTimeBatIsNotAwake() {

        final GameWorld gameWorld = new GameWorld();
        final Bat anotherBatModel = new Bat();
        anotherBatModel.setSleepTime(sleepTime);
        gameWorld.add(anotherBatModel);
        gameWorld.pulse(sleepTime-0.1);

        assertThat(anotherBatModel.isAwake(), is(false));
        assertThat(anotherBatModel.isInMotion(), is(false));
        assertThat(anotherBatModel.isAnimated(), is(false));
    }

    @Test
    public void atSleepTimeBatIsAwake() {

        final GameWorld gameWorld = new GameWorld();
        final Bat anotherBatModel = new Bat();
        anotherBatModel.setSleepTime(sleepTime);
        anotherBatModel.setXPosition(528.0);
        anotherBatModel.setYPosition(96.0);
        anotherBatModel.setDirection(Direction.Up);
        gameWorld.add(anotherBatModel);
        gameWorld.pulse(sleepTime);

        assertThat(anotherBatModel.isAwake(), is(true));
        assertThat(anotherBatModel.isInMotion(), is(true));
        assertThat(anotherBatModel.isAnimated(), is(true));
    }

    @Test
    public void canChangePosition() {

        assertThat(bat.canChangePosition(), is(true));
    }
}
