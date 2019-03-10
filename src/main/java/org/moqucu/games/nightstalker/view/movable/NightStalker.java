package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.Indices;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.view.immovable.Weapon;
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
public class NightStalker extends ArtificiallyMovedSprite {

    enum States {
        asleep, awake, moving
    }

    enum Events {
        wakeUp, move, stop
    }

    private StateMachine<States, Events> stateMachine;

    private Direction direction = Direction.Down;

    private Map<Direction, Indices> frameBoundaries = Map.of(
            Direction.Up, Indices.builder().lower(1).upper(2).build(),
            Direction.Down, Indices.builder().lower(1).upper(2).build(),
            Direction.Right, Indices.builder().lower(11).upper(18).build(),
            Direction.Left, Indices.builder().lower(3).upper(10).build()
    );

    private MazeGraph mazeGraph;

    private Weapon weapon = null;

    @SneakyThrows
    public NightStalker() {

        super();

        setImage(new Image(translate("images/night-stalker.png")));
        setAutoReversible(21);
        setVelocity(70);

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
                .timerOnce(2000)
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
        animation.setAutoReverse(false);
        animation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
        Point2D deltaNode = getNextNode().subtract(getPreviousNode());

        if (deltaNode.getX() < 0)
            direction = Direction.Left;
        else if (deltaNode.getX() > 0)
            direction = Direction.Right;
        else if (deltaNode.getY() < 0)
            direction = Direction.Up;
        else if (deltaNode.getY() > 0)
            direction = Direction.Down;
        animation.play();
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
                    (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph-night-stalker.json")
                            .getInputStream())
            );

        return mazeGraph;
    }
}
