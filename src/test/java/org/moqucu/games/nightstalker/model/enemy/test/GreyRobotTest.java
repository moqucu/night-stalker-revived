package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GreyRobotTest {

    /*
    private enum States {Inactive, Active, Stopped, Moving, SlowlyMoving, MovingFast, FallingApart, Fired}

    private enum Events {spawn, move, faster, stop, fallApart, becomeInactive, fire, canFireAgain}'

            setAnimationProperties(
                Map.of
                        (
                                States.Inactive, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(10).upper(10).build())
                                        .build(),
                                States.Stopped, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.SlowlyMoving, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(1).build())
                                        .build(),
                                States.MovingFast, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(1).build())
                                        .build(),
                                States.FallingApart, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(6).upper(9).build())
                                        .build()
                        )
        );

           public static AudioClip shootSound
            = new AudioClip(Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/shoot.wav").toString());

    public void fire(NightStalker player) {
        Direction playerDirection = this.getPlayerShootable(player.getCurrentLocation());

        if (playerDirection == Direction.Undefined) {
            return;
        }

        if (bullet != null && bullet.isShot())
            return;
        getMaze().getAllRobotBullets().stream().findAny().ifPresent(bullet -> this.bullet = bullet);
        bullet.shot(playerDirection, this.getCurrentLocation());
        shootSound.setVolume(0.1f);
        shootSound.play();
    }

     */

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

        assertThat(greyRobot.getVelocity(), is(15.));
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
