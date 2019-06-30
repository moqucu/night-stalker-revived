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
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;

import java.util.*;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends SleepingSprite implements Hittable, Collidable {

    private enum States {Asleep, Awake, Moving, Hit}

    private enum Events {wakeUp, move, stop, hit, spawn}

    private StateMachine<States, Events> stateMachine;

    public Bat() {

        super();

        setImage(new Image(translate("images/bat.png")));

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

        sleepTimeInMillisProperty().addListener((observableValue, number, t1) -> {

            stopAndDeconstructStateMachine();
            configureAndStartStateMachine();
        });

        configureAndStartStateMachine();
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
    }

    @SuppressWarnings("unchecked")
    private void stopAndDeconstructStateMachine() {

        stateMachine.stop();
        stateMachine.removeStateListener(this);
        stateMachine = null;
    }

    @SuppressWarnings("unchecked")
    private void configureAndStartStateMachine() {

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(this);
        stateMachine.start();
    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Asleep)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.Asleep)
                .action(this::timeToWakeUp)
                .timerOnce(getSleepTimeInMillis())
                .and()
                .withExternal()
                .source(States.Asleep)
                .action(this::wokeUp)
                .target(States.Awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Awake)
                .target(States.Moving)
                .action(this::startedToMove)
                .event(Events.move)
                .and()
                .withExternal()
                .source(States.Moving)
                .target(States.Awake)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Moving)
                .target(States.Hit)
                .event(Events.hit)
                .and()
                .withInternal()
                .source(States.Hit)
                .action(this::timeToRelocateAndFallAsleep)
                .timerOnce(getSpawnTimeInMillis())
                .and()
                .withExternal()
                .source(States.Hit)
                .target(States.Asleep)
                .event(Events.spawn)
        ;

        return builder.build();
    }

    @Override
    public void transitionEnded(Transition transition) {

        super.transitionEnded(transition);

        if (transition.getTarget().getId() == States.Awake)
            stateMachine.sendEvent(Events.move);
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug("timeToWakeUp: {}", stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }

    private void wokeUp(StateContext stateContext) {

        log.debug("wokeUp: {}", stateContext);
        animateMeFromStart();
    }

    private void startedToMove(StateContext stateContext) {

        log.debug("startedToMove: {}", stateContext);
        computeNextMoveAnimationBasedOnRandomDirection();
        moveMeFromStart();
    }

    private void timeToRelocateAndFallAsleep(StateContext<States, Events> statesEventsStateContext) {

        log.debug("timeToRelocateAndFallAsleep: {}", statesEventsStateContext);
        this.translateXProperty().set(this.spawnCoordinateXProperty().get());
        this.translateYProperty().set(this.spawnCoordinateYProperty().get());
        stateMachine.sendEvent(Events.spawn);
    }

    @Override
    public void hitBy(Collidable collidableObject) {

        if (collidableObject instanceof Bullet) {

            log.debug("Hit by bullet...");
            stopMovingMe();
            stateMachine.sendEvent(Events.hit);
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
        switch (stateMachine.getState().getId()) {
            case Moving:
            case Asleep:
            case Awake:
                return true;
            default:
                return false;
        }
    }
}
