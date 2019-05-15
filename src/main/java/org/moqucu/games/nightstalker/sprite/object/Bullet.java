package org.moqucu.games.nightstalker.sprite.object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
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
public class Bullet extends AnimatedSprite {

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
        setAutoReversible(false);
        setFrameDurationInMillis(500);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
            }
        });
        stateMachine.start();
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
    }

    void shot(Point2D startPoint) {

        log.info("Starting point: {}", startPoint);

        relocate(startPoint.getX(), startPoint.getY());
        stateMachine.sendEvent(Events.shoot);
    }

    private void loaded(StateContext stateContext) {

        log.debug("loaded: {}", stateContext);
    }

}
