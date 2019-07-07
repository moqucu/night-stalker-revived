package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;

import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends SleepingSprite implements Hittable, Collidable {

    private enum States {Inactive, Active, Stopped, Moving, SlowlyMoving, MovingFast, FallingApart}

    private enum Events {spawn, move, faster, stop, fallApart, becomeInactive}

    private StateMachine<States, Events> stateMachine;

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
                                        .frameIndices(Indices.builder().lower(6).upper(9).build())
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
                .state(States.Stopped)
                .state(States.Moving)
                .and()
                .withStates()
                .parent(States.Moving)
                .initial(States.SlowlyMoving)
                .state(States.SlowlyMoving)
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
                .action(stateContext -> stateMachine.sendEvent(Events.becomeInactive))
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
                computeNextMoveAnimationBasedOnRandomDirection();
                moveMeFromStart();
                break;
            case MovingFast:
                computeNextMoveAnimationBasedOnRandomDirection();
                moveMeFromStart();
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
            stateMachine.sendEvent(Events.fallApart);
        }
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
}
