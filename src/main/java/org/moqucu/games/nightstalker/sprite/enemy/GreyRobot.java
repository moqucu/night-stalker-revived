package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.SleepingSprite;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends SleepingSprite {

    private enum States {Asleep, Awake, SlowlyMoving, FastMoving}

    private enum Events {wakeUp, move, stop}

    private StateMachine<States, Events> stateMachine;

    private StateMachineListener<States, Events> stateMachineListener = new StateMachineListenerAdapter<>() {

        @Override
        public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            if (transition.getTarget().getId().equals(States.Awake))
                stateMachine.sendEvent(Events.move);
        }
    };

    public GreyRobot() {

        super();

        setImage(new Image(translate("images/grey-robot.png")));

        setAutoReversible(false);
        setFrameIndices(Indices.builder().lower(1).upper(2).build());

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
    private StateMachine<GreyRobot.States, GreyRobot.Events> buildStateMachine() {

        StateMachineBuilder.Builder<GreyRobot.States, GreyRobot.Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Asleep)
                .states(EnumSet.allOf(GreyRobot.States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.Asleep)
                .action(this::timeToWakeUp)
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.Asleep)
                .action(this::wokeUp)
                .target(States.Awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Awake)
                .target(States.SlowlyMoving)
                .action(this::startedToMove)
                .event(Events.move)
                .and()
                .withInternal()
                .source(States.SlowlyMoving)
                .action(this::timeToMoveFaster)
                .timerOnce(4000)
                .and()
                .withExternal()
                .source(States.SlowlyMoving)
                .target(States.FastMoving)
                .event(Events.move);

        return builder.build();
    }

    private void timeToMoveFaster(StateContext stateContext) {

        log.debug("timeToMoveFaster: {}", stateContext);
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
}
