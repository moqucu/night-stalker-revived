package org.moqucu.games.nightstalker.sprite.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.ArtificiallyMovableSprite;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@SuppressWarnings("unused")
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class GreyRobotPartOne extends ArtificiallyMovableSprite implements Collidable {

    enum States {Flying, Hidden}

    enum Events {fly, hide}

    StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Flying, Indices.builder().lower(2).upper(3).build(),
            States.Hidden, Indices.builder().lower(10).upper(10).build()
    );

    public GreyRobotPartOne() {

        super();
        setImage(new Image(translate("images/grey-robot.png")));
        setFrameBoundaries(frameBoundaries);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
            }
        });
        stateMachine.start();
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.hide));

        animateMeFromStart();
    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Flying)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.Hidden)
                .action(this::fly)
                .target(States.Flying)
                .event(Events.fly)
                .and()
                .withExternal()
                .source(States.Flying)
                .target(States.Hidden)
                .action(this::fly)
                .event(Events.hide);

        return builder.build();
    }

    private void fly(StateContext<States, Events> stateContext) {

        log.info("shot: {}", stateContext);
        moveMeFromStart();
    }

    void shot(Direction direction, Point2D startPoint) {

        if (stateMachine.getState().getId().equals(States.Flying))
            return;

        log.info("Starting point: {}", startPoint);

        Point2D endPoint = getMazeGraph().getFurthestReachableNode(startPoint, direction);

        switch (direction) {
            case Up:
                setMoveAnimation(startPoint, endPoint.add(0, -16));
                break;
            case Down:
                setMoveAnimation(startPoint, endPoint.add(0, 16));
                break;
            case Left:
                setMoveAnimation(startPoint, endPoint.add(-16, 0));
                break;
            case Right:
                setMoveAnimation(startPoint, endPoint.add(16, 0));
                break;
            default:
                setMoveAnimation(startPoint, startPoint);
        }
        stateMachine.sendEvent(Events.fly);
    }

    @Override
    public boolean isCollidable() {

        return stateMachine.getState().getId().equals(States.Flying);
    }

    private void hide(StateContext<States, Events> stateContext) {

        log.error(
                "At location {}with state loaded and following state context: {}",
                getCurrentLocation(),
                stateContext
        );
        stopMovingMe();
    }

}
