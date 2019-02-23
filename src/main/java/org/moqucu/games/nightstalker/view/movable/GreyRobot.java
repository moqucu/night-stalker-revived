package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends ArtificiallyMovedSprite {

    enum States {
        awake, moving
    }

    enum Events {
        move, stop
    }

    StateMachine<GreyRobot.States, GreyRobot.Events> stateMachine;

    public GreyRobot() {

        setImage(new Image(translate("images/grey-robot.png")));
        setNumberOfFrames(2);
        setVelocity(35);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<GreyRobot.States, GreyRobot.Events> transition) {

                if (transition.getTarget().getId().equals(GreyRobot.States.awake))
                    stateMachine.sendEvent(GreyRobot.Events.move);
            }
        });
        stateMachine.start();

        playAnimation();
    }

    @SneakyThrows
    private StateMachine<GreyRobot.States, GreyRobot.Events> buildStateMachine() {

        StateMachineBuilder.Builder<GreyRobot.States, GreyRobot.Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.awake)
                .states(EnumSet.allOf(GreyRobot.States.class));

        builder.configureTransitions()
                  .withExternal()
                .source(GreyRobot.States.awake)
                .target(GreyRobot.States.moving)
                .action(this::startedToMove)
                .event(GreyRobot.Events.move)
                .and()
                .withExternal()
                .source(GreyRobot.States.moving)
                .target(GreyRobot.States.awake)
                .event(GreyRobot.Events.stop);

        return builder.build();
    }

    private void startedToMove(StateContext stateContext) {

        log.debug("startedToMove: {}", stateContext);
        Animation animation = prepareAnimationForMovingSpriteRandomlyAlongMazeGraph();
        animation.setOnFinished(actionEvent -> stateMachine.sendEvent(GreyRobot.Events.stop));
        animation.play();
    }
}
