package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Indices;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;
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
public class Bat extends SleepingSprite {

    private enum States {asleep, awake, moving}

    private enum Events {wakeUp, move, stop}

    private MazeGraph mazeGraph;

    private StateMachine<States, Events> stateMachine;

    private StateMachineListener<States, Events> stateMachineListener = new StateMachineListenerAdapter<>() {

        @Override
        public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            if (transition.getTarget().getId().equals(States.awake))
                stateMachine.sendEvent(Events.move);
        }
    };

    public Bat() {

        super();

        setImage(new Image(translate("images/bat.png")));
        setFrameIndices(Indices.builder().lower(1).upper(5).build());

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
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.asleep)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.asleep)
                .action(this::timeToWakeUp)
                .timerOnce(getSleepTimeInMillis())
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
        playAnimation();
    }

    private void startedToMove(StateContext stateContext) {

        log.debug("startedToMove: {}", stateContext);
        Animation animation = prepareAnimationForMovingSpriteRandomlyAlongMazeGraph();
        animation.setOnFinished(actionEvent -> stateMachine.sendEvent(Bat.Events.stop));
        animation.play();
    }

    @SneakyThrows
    protected MazeGraph getMazeGraph() {

        if (mazeGraph == null)
            mazeGraph = new MazeGraph(
                    (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph.json")
                            .getInputStream())
            );

        return mazeGraph;
    }
}
