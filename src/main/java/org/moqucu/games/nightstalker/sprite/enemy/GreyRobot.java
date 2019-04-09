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

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends SleepingSprite {

    private enum States {Offline, Stopped, Moving, SlowlyMoving, FastMoving}

    private enum Events {wakeUp, move, moveFaster, stop}

    private StateMachine<States, Events> stateMachine;

    private StateMachineListener<States, Events> stateMachineListener = new StateMachineListenerAdapter<>() {

        @Override
        public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            switch (transition.getTarget().getId()) {
                case Stopped:
                    stateMachine.sendEvent(Events.move);
                    break;
                case FastMoving:
                    setVelocity(50);
                    break;
            }
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
                .initial(States.Offline)
                .state(States.Stopped)
                .state(States.Moving)
                .and()
                .withStates()
                .parent(States.Moving)
                .initial(States.SlowlyMoving)
                .state(States.FastMoving);

        builder.configureTransitions()
                .withInternal()
                .source(States.Offline)
                .action(this::timeToWakeUp)
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.Offline)
                .action(this::wokeUp)
                .target(States.Stopped)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Stopped)
                .target(States.Moving)
                .action(this::startedToMove)
                .event(Events.move)
                .and()
                .withExternal()
                .source(States.Moving)
                .target(States.Stopped)
                .event(Events.stop)
                .and()
                .withInternal()
                .source(States.SlowlyMoving)
                .action(this::timeToMoveFaster)
                .timerOnce(4000)
                .and()
                .withExternal()
                .source(States.SlowlyMoving)
                .target(States.FastMoving)
                .event(Events.moveFaster);

        return builder.build();
    }

    private void timeToMoveFaster(StateContext stateContext) {

        log.debug("timeToMoveFaster: {}", stateContext);
        stateMachine.sendEvent(Events.moveFaster);
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
