package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.SleepingSprite;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.*;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends SleepingSprite implements Hittable {

    private enum States {Asleep, Awake, Moving, Dying}

    private enum Events {wakeUp, move, stop, die}

    private StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Asleep, Indices.builder().lower(0).upper(0).build(),
            States.Awake, Indices.builder().lower(0).upper(0).build(),
            States.Moving, Indices.builder().lower(1).upper(5).build(),
            States.Dying, Indices.builder().lower(6).upper(9).build()
    );


    private StateMachineListener<States, Events> stateMachineListener
            = new StateMachineListenerAdapter<>() {

        @Override
        public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            log.debug("State changed to {}", transition.getTarget().getId());

            setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));

            switch (transition.getTarget().getId()) {

                case Awake:
                    stateMachine.sendEvent(Events.move);
                    break;
             }
       }
    };

    public Bat() {

        super();

        setImage(new Image(translate("images/bat.png")));

        sleepTimeInMillisProperty().addListener((observableValue, number, t1) -> {

            stopAndDeconstructStateMachine();
            configureAndStartStateMachine();
        });

        configureAndStartStateMachine();
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
    }

    private void stopAndDeconstructStateMachine() {

        stateMachine.stop();
        stateMachine.removeStateListener(stateMachineListener);
        stateMachine = null;
    }

    private void configureAndStartStateMachine() {

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(stateMachineListener);
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
                .target(States.Dying)
                .event(Events.die);

        return builder.build();
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

    @Override
    public void detectCollision(Set<AnimatedSprite> nearbySprites) {

        nearbySprites.forEach(animatedSprite -> {

            if ((animatedSprite instanceof Bullet)
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())) {

                log.debug("Hit by bullet, dying.");
                stopMovingMe();
                stateMachine.sendEvent(Events.die);
            }
        });
    }
}
