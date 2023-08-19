package org.moqucu.games.nightstalker.model.hero.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;
import org.moqucu.games.nightstalker.model.hero.NightStalker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NightStalkerTest {

    /*

        enum States {Inactive, Active, Paused, Dying, Stopped, Moving, Right, Left, Vertically, Fainting, Fainted}

    enum Events {spawn, moveRight, moveVertically, moveLeft, stop, faint, fallAsleep, die, becomeInactive, wakeUp}

   private Direction direction;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty lives = new SimpleIntegerProperty(6);

    private AudioClip beingZappedSound = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/zap.wav").toString()
    );

        setAnimationProperties(
                Map.of
                        (
                                States.Inactive, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(24).upper(24).build())
                                        .build(),
                                States.Stopped, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.Vertically, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(1).upper(2).build())
                                        .build(),
                                States.Right, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(11).upper(18).build())
                                        .build(),
                                States.Left, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(3).upper(10).build())
                                        .build(),
                                States.Fainting, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(250)
                                        .frameIndices(Indices.builder().lower(19).upper(20).build())
                                        .build(),
                                States.Fainted, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(21).upper(21).build())
                                        .build(),
                                States.Dying, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(22).upper(23).build())
                                        .build()
                        )
        );

     */

    private final NightStalker nightStalker = new NightStalker();

    @Test
    public void ofTypeMovableObject() {

        assertThat(nightStalker, isA(MovableObject.class));
    }

    @Test
    public void hasRunningProperty() {

        assertThat(nightStalker, hasProperty("running"));
    }

    @Test
    public void runningPropertyOfTypeBoolean() {

        assertThat(nightStalker.isRunning(), isA(Boolean.class));
    }

    @Test
    public void pointsToNightStakerGraph() {

        assertThat(nightStalker.getMazeGraphFileName(), is("/json/maze-graph-night-stalker.json"));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(nightStalker.getImageMapFileName(), is("/images/night-stalker.png"));
    }

    @Test
    public void initialImageIndexIsZero() {

        final NightStalker anotherNightStalker = new NightStalker();
        assertThat(anotherNightStalker.getInitialImageIndex(), is(0));
    }

    @Test
    public void initialLowerAnimationIndexIsZero() {

        final NightStalker anotherNightStalker = new NightStalker();
        assertThat(anotherNightStalker.getLowerAnimationIndex(), is(0));
    }

    @Test
    public void initialUpperAnimationIndexIsZero() {

        final NightStalker anotherNightStalker = new NightStalker();
        assertThat(anotherNightStalker.getUpperAnimationIndex(), is(0));
    }

    @Test
    public void stopPutsRunningToFalseAndResetsAnimationIndicesToZero() {

        final NightStalker anotherNightStalker = new NightStalker();
        anotherNightStalker.setRunning(true);
        anotherNightStalker.setLowerAnimationIndex(1);
        anotherNightStalker.setUpperAnimationIndex(2);
        anotherNightStalker.setAnimated(true);
        anotherNightStalker.setInMotion(false);
        anotherNightStalker.setLowerAnimationIndex(2);

        anotherNightStalker.stop();

        assertThat(anotherNightStalker.isRunning(), is(false));
        assertThat(anotherNightStalker.getLowerAnimationIndex(), is(0));
        assertThat(anotherNightStalker.getUpperAnimationIndex(), is(0));
        assertThat(anotherNightStalker.isAnimated(), is(false));
        assertThat(anotherNightStalker.isInMotion(), is(false));
    }

    @Test
    public void frameRateIsTen() {

        assertThat(nightStalker.getFrameRate(), is(10));
    }

    @Test
    public void velocityIsThirty() {

        assertThat(nightStalker.getVelocity(), is(30.));
    }

    @Test
    public void initialYPositionIsOneHundredFortyFour() {

        final NightStalker anotherNightStalker = new NightStalker();

        assertThat(anotherNightStalker.getYPosition(), is(144.));
    }

    @Test
    public void initialYPositionIsTwoHundredEightyEight() {

        final NightStalker anotherNightStalker = new NightStalker();

        assertThat(anotherNightStalker.getXPosition(), is(288.));
    }

    @Test
    public void whenRunningUpItWillSetDirectionToUpAndRunningToTrue() {

        final NightStalker anotherNightStalker = new NightStalker();
        anotherNightStalker.run(Direction.Up);

        assertThat(anotherNightStalker.getDirection(), is(Direction.Up));
        assertThat(anotherNightStalker.isRunning(), is(true));

        anotherNightStalker.stop();
        assertThat(anotherNightStalker.getDirection(), is(Direction.Up));
        assertThat(anotherNightStalker.isRunning(), is(false));
    }

    @Test
    public void isOfTypeResettable() {

        assertThat(nightStalker, isA(Resettable.class));
    }

    @Test
    public void afterResetNightStalkerIsInTheRightPositionAndState() {

        final NightStalker anotherNightStalker = new NightStalker();
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(anotherNightStalker);

        anotherNightStalker.setRunning(true);
        gameWorld.pulse(1000);

        assertThat(anotherNightStalker.isRunning(), is(true));
        assertThat(anotherNightStalker.getDirection(), is(Direction.Up));
        assertThat(anotherNightStalker.getYPosition(), is(lessThan(144.0)));
        assertThat(anotherNightStalker.isAnimated(), is(true));
        assertThat(anotherNightStalker.isInMotion(), is(true));

        anotherNightStalker.reset();

        assertThat(anotherNightStalker.isRunning(), is(false));
        assertThat(anotherNightStalker.getDirection(), is(Direction.Up));
        assertThat(anotherNightStalker.getYPosition(), is(144.0));
        assertThat(anotherNightStalker.isAnimated(), is(false));
        assertThat(anotherNightStalker.isInMotion(), is(false));
    }
}
