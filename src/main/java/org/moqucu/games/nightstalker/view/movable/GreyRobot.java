package org.moqucu.games.nightstalker.view.movable;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Indices;
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

    private enum States {asleep, awake, moving}

    private enum Events {wakeUp, move, stop}

    private StateMachine<States, Events> stateMachine;

    private StateMachineListener<States, Events> stateMachineListener = new StateMachineListenerAdapter<>() {

        @Override
        public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            if (transition.getTarget().getId().equals(States.awake))
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
                .initial(States.asleep)
                .states(EnumSet.allOf(GreyRobot.States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.asleep)
                .action(this::timeToWakeUp)
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.asleep)
                .action(this::wokeUp)
                .target(States.awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.awake)
                .target(States.moving)
                .action(this::startedToMove)
                .event(Events.move)
                .and()
                .withExternal()
                .source(States.moving)
                .target(States.awake)
                .event(Events.stop);

        return builder.build();
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug("timeToWakeUp: {}", stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }

    private void wokeUp(StateContext stateContext) {

        log.debug("wokeUp: {}", stateContext);
        startAnimatingMe();
    }

    private void startedToMove(StateContext stateContext) {

        log.debug("startedToMove: {}", stateContext);
        computeNextMoveAnimationBasedOnRandomDirection(actionEvent -> stateMachine.sendEvent(Events.stop));
        startMovingMe();
    }
}
