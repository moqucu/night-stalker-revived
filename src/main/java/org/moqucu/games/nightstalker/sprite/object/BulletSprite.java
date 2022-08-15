package org.moqucu.games.nightstalker.sprite.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Bullet;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.ArtificiallyMovableSprite;
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
public class BulletSprite extends ArtificiallyMovableSprite implements Bullet {

    enum States {Loaded, Shot}

    enum Events {shoot, load}

    StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Loaded, Indices.builder().lower(0).upper(0).build(),
            States.Shot, Indices.builder().lower(1).upper(1).build()
    );

    public BulletSprite() {

        super();
        setImage(new Image(translate("images/bullet.png")));
        setFrameBoundaries(frameBoundaries);
        setAutoReversible(false);
        setFrameDurationInMillis(500);
        setVelocity(150);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
            }
        });
        stateMachine.start();
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.load));

        animateMeFromStart();
    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Loaded)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.Loaded)
                .action(this::shot)
                .target(States.Shot)
                .event(Events.shoot)
                .and()
                .withExternal()
                .source(States.Shot)
                .target(States.Loaded)
                .action(this::loaded)
                .event(Events.load);

        return builder.build();
    }

    private void shot(StateContext stateContext) {

        log.info("shot: {}", stateContext);
        moveMeFromStart();
    }

    public void shot(Direction direction, Point2D startPoint) {

        if (stateMachine.getState().getId().equals(States.Shot))
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
        stateMachine.sendEvent(Events.shoot);
    }

    @Override
    public boolean isCollidable() {

        return stateMachine.getState().getId().equals(States.Shot);
    }

    private void loaded(StateContext stateContext) {

        log.error(
                "At location {} with state loaded and following state context: {}",
                getCurrentLocation(),
                stateContext
        );
        stopMovingMe();
    }

}
