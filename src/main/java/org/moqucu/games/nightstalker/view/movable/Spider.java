package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class Spider extends ArtificiallyMovedSprite {

    enum States {
        asleep, awake, moving
    }

    enum Events {
        wakeUp, move, stop
    }

    StateMachine<States, Events> stateMachine;

    public Spider() {

        super();

        setImage(new Image(translate("images/spider.png")));
        setNumberOfFrames(9);

        setVelocity(35);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                if (transition.getTarget().getId().equals(States.awake))
                    stateMachine.sendEvent(Events.move);
            }
        });
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
                .timerOnce(500)
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
        animation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
        animation.play();
    }

}
