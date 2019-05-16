package org.moqucu.games.nightstalker.sprite.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.ArtificiallyMovableSprite;
import org.moqucu.games.nightstalker.sprite.enemy.Bat;
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
public class Bullet extends ArtificiallyMovableSprite {

    enum States {Loaded, Shot}

    enum Events {shoot, load}

    StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Loaded, Indices.builder().lower(0).upper(0).build(),
            States.Shot, Indices.builder().lower(1).upper(1).build()
    );

    public Bullet() {

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

        log.error("shot: {}", stateContext);
        moveMeFromStart();
    }

    void shot(Direction direction, Point2D startPoint) {

        if (stateMachine.getState().getId().equals(States.Shot))
            return;

        log.info("Starting point: {}", startPoint);

        Point2D endPoint;
        switch (direction) {
            case Up:
                endPoint = new Point2D(startPoint.getX(), 0);
                break;
            case Down:
                endPoint = new Point2D(startPoint.getX(), 12 * 32);
                break;
            case Left:
                endPoint = new Point2D(0, startPoint.getY());
                break;
            case Right:
                endPoint = new Point2D(20 * 32, startPoint.getY());
                break;
            default:
                endPoint = startPoint;
        }
        setMoveAnimation(startPoint, endPoint);
        stateMachine.sendEvent(Events.shoot);
    }

    private void loaded(StateContext stateContext) {

        log.error(
                "At location {}with state loaded and following state context: {}",
                getCurrentLocation(),
                stateContext
        );
        stopMovingMe();
    }

}
