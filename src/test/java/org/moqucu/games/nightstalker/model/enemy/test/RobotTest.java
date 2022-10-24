package org.moqucu.games.nightstalker.model.enemy.test;

public class RobotTest {

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
}
