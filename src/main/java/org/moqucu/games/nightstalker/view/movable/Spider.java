package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.Indices;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class Spider extends ArtificiallyMovedSprite {

    enum States {asleep, awake, movingHorizontally, movingVertically}

    enum Events {wakeUp, moveHorizontally, moveVertically, stop}

    private StateMachine<States, Events> stateMachine;

    private MazeGraph mazeGraph;

    private Direction direction = Direction.Down;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.movingVertically, Indices.builder().lower(1).upper(2).build(),
            States.movingHorizontally, Indices.builder().lower(3).upper(4).build()
    );

    public Spider() {

        super();

        setImage(new Image(translate("images/spider.png")));
        setStillImageIndex(0);

        setAutoReversible(5);

        setFrameDurationInMillis(50);
        setVelocity(35);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                if (transition.getTarget().getId().equals(States.awake))
                    stateMachine.sendEvent(Events.moveHorizontally);
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
                .target(States.movingHorizontally)
                .event(Events.moveHorizontally)
                .action(this::startedToMove)
                .and()
                .withExternal()
                .source(States.movingHorizontally)
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

        Animation animation = prepareAnimationForMovingSpriteRandomlyAlongMazeGraph();
        animation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));

        Point2D deltaNode = getNextNode().subtract(getPreviousNode());
        if (deltaNode.getX() != 0)
            stateMachine.sendEvent(Events.moveHorizontally);
        else if (deltaNode.getY() != 0)
            stateMachine.sendEvent(Events.moveVertically);

        animation.play();


        playAnimation();
    }

    private void startedToMoveHorizontally(StateContext stateContext) {

        log.debug("startedToMoveHorizontally: {}", stateContext);


    }

    @Override
    public void interpolate(Double fraction) {

        Indices indices = getFrameBoundaries().get(direction);
        setViewport(
                frames.get(indices.getLower()
                        + Long.valueOf(Math.round(fraction * (indices.getUpper() - indices.getLower()))).intValue())
        );
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
