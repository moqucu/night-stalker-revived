package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Line;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.Approachable;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.Sprite;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartOne;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartTwo;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.transition.TransitionKind;

import java.util.Map;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends SleepingSprite implements Hittable, Collidable, Approachable {

    private enum States {Inactive, Active, Stopped, Moving, SlowlyMoving, MovingFast, FallingApart}

    private enum Events {spawn, move, faster, stop, fallApart, becomeInactive}

    private StateMachine<States, Events> stateMachine;

    private GreyRobotPartOne partOne;
    private GreyRobotPartTwo partTwo;

    private AudioClip destruction = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/destruct.wav").toString()
    );

    public GreyRobot() {

        super();

        setImage(new Image(translate("images/grey-robot.png")));
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
                                        .frameIndices(Indices.builder().lower(10).upper(10).build())
                                        .build()
                        )
        );

        stateMachine = buildStateMachine();

        //noinspection unchecked
        stateMachine.addStateListener(this);
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
        stateMachine.start();
    }

    @SneakyThrows
    private StateMachine<GreyRobot.States, GreyRobot.Events> buildStateMachine() {

        StateMachineBuilder.Builder<GreyRobot.States, GreyRobot.Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Inactive)
                .state(States.Active)
                .state(States.FallingApart)
                .and()
                .withStates()
                .parent(States.Active)
                .initial(States.Stopped)
                .state(States.Moving)
                .and()
                .withStates()
                .parent(States.Moving)
                .initial(States.SlowlyMoving)
                .state(States.MovingFast);

        builder.configureTransitions()
                .withInternal()
                .source(States.Inactive)
                .action(stateContext -> stateMachine.sendEvent(Events.spawn))
                .timerOnce(500)
                .and()
                .withExternal()
                .source(States.Inactive)
                .event(Events.spawn)
                .target(States.Stopped)
                .and()
                .withExternal()
                .source(States.Stopped)
                .event(Events.move)
                .target(States.SlowlyMoving)
                .and()
                .withInternal()
                .source(States.SlowlyMoving)
                .action(stateContext -> stateMachine.sendEvent(Events.faster))
                .timerOnce(4000)
                .and()
                .withExternal()
                .source(States.SlowlyMoving)
                .event(Events.faster)
                .target(States.MovingFast)
                .event(Events.move)
                .and()
                .withExternal()
                .source(States.Moving)
                .event(Events.stop)
                .target(States.Stopped)
                .and()
                .withExternal()
                .source(States.Active)
                .event(Events.fallApart)
                .target(States.FallingApart)
                .and()
                .withInternal()
                .source(States.FallingApart)
                .action(this::fallApart)
                .timerOnce(getSleepTimeInMillis())
                .and()
                .withExternal()
                .source(States.FallingApart)
                .event(Events.becomeInactive)
                .target(States.Inactive);

        return builder.build();
    }

    @Override
    public void transitionEnded(Transition transition) {

        super.transitionEnded(transition);

        switch ((States)transition.getTarget().getId()) {

            case Inactive:
                translateXProperty().set(spawnCoordinateXProperty().get());
                translateYProperty().set(spawnCoordinateYProperty().get());
                break;
            case Stopped:
                stopMovingMe();
                stateMachine.sendEvent(Events.move);
                break;
            case SlowlyMoving:
                if (transition.getKind().equals(TransitionKind.EXTERNAL)) {

                    computeNextMoveAnimationBasedOnRandomDirection();
                    moveMeFromStart();
                }
                break;
            case MovingFast:
                setVelocity(50);
                break;
            case FallingApart:
                stopMovingMe();
                break;
        }

    }

    @Override
    public void hitBy(Collidable collidableObject) {

        if (collidableObject instanceof Bullet) {

            log.debug("Hit by bullet...");
            stopMovingMe();
            notifyHitListenerAboutHit();
            stateMachine.sendEvent(Events.fallApart);
        }
    }

    private void fallApart(StateContext stateContext) {

        log.trace("fallApart: {}", stateContext);
        destruction.setVolume(0.1f);
        destruction.play();

        partOne.startFlying(this.getDirection(), this.getCurrentLocation());
        partTwo.startFlying(this.getDirection(), this.getCurrentLocation());
        stateMachine.sendEvent(Events.becomeInactive);
    }

    @Override
    public boolean isCollidable() {

        return isActive();
    }

    @Override
    public boolean isHittable() {

        return isActive();
    }

    private boolean isActive() {

        return stateMachine.getState().getId().equals(States.Active);
    }

    @Override
    public void approachedBy(Set<Sprite> sprite) {

        sprite.forEach(log::debug);
    }

    @Override
    public Line getLineOfSight() {

        Point2D currentNode = getCurrentLocation();
        Direction direction = getDirection();
        if (currentNode == null)
            log.error("current node is null!");
        if (direction == null)
            log.error("direction is null!");
        Point2D furthestReachableNode = getEnemyMazeGraph().getFurthestReachableNode(currentNode, direction);

        return new Line(
                currentNode.getX(),
                currentNode.getY(),
                furthestReachableNode.getX(),
                furthestReachableNode.getY()
        );
    }
}
