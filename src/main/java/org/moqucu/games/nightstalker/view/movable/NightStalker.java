package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.model.Indices;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.Updatable;
import org.moqucu.games.nightstalker.view.immovable.Weapon;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends ArtificiallyMovedSprite implements Updatable {

    enum States {Awake, MovingLeft, MovingRight, MovingVertically, Fainting}

    enum Events {moveLeft, moveRight, moveVertically, stop, faint, wakeUp}

    private StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Awake, Indices.builder().lower(0).upper(0).build(),
            States.MovingVertically, Indices.builder().lower(1).upper(2).build(),
            States.MovingRight, Indices.builder().lower(21).upper(28).build(),
            States.MovingLeft, Indices.builder().lower(3).upper(10).build(),
            States.Fainting, Indices.builder().lower(19).upper(20).build()
    );

    private MazeGraph mazeGraph;

    private Weapon weapon = null;

    private Animation translateAnimation;

    @SneakyThrows
    public NightStalker() {

        super();

        setImage(new Image(translate("images/night-stalker.png")));
        setAutoReversible(false);
        setVelocity(70);
        setFrameDurationInMillis(200);

        setFocusTraversable(true);
        setOnKeyPressed(this::handleKeyPressedEvent);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                log.debug("State changed to {}", transition.getTarget().getId());

                switch (transition.getTarget().getId()) {

                    case Awake:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        stopAnimation();
                        break;
                    case MovingLeft:
                    case MovingRight:
                    case MovingVertically:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        playAnimation();
                        translateAnimation.play();
                        break;
                    case Fainting:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        playAnimation();
                        break;
                }
            }
        });
        stateMachine.start();
    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Awake)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.Awake)
                .target(States.MovingLeft)
                .event(Events.moveLeft)
                .and()
                .withExternal()
                .source(States.MovingLeft)
                .target(States.Awake)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Awake)
                .target(States.MovingRight)
                .event(Events.moveRight)
                .and()
                .withExternal()
                .source(States.Awake)
                .target(States.Fainting)
                .event(Events.faint)
                .and()
                .withInternal()
                .source(States.Fainting)
                .action(this::timeToWakeUp)
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.Fainting)
                .target(States.Awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.MovingRight)
                .target(States.Awake)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Awake)
                .target(States.MovingVertically)
                .event(Events.moveVertically)
                .and()
                .withExternal()
                .source(States.MovingVertically)
                .target(States.Awake)
                .event(Events.stop);

        return builder.build();
    }

    private void handleKeyPressedEvent(KeyEvent keyEvent) {

        log.debug("Key pressed: {}", keyEvent.getCode());

        if (stateMachine.getState().getId() != States.Awake)
            return;

        Point2D currentNode = getCurrentNode();
        List<Point2D> adjacentNodes = getAdjacentNodes(currentNode);

        switch (keyEvent.getCode()) {

            case UP:
                adjacentNodes
                        .stream()
                        .filter(node -> node.getX() == currentNode.getX() && node.getY() < currentNode.getY())
                        .findFirst()
                        .ifPresent(point2D -> {
                            translateAnimation = calculateTranslateTransition(currentNode, point2D);
                            translateAnimation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
                            stateMachine.sendEvent(Events.moveVertically);
                        });
                break;
            case DOWN:
                adjacentNodes
                        .stream()
                        .filter(node -> node.getX() == currentNode.getX() && node.getY() > currentNode.getY())
                        .findFirst()
                        .ifPresent(point2D -> {
                            translateAnimation = calculateTranslateTransition(currentNode, point2D);
                            translateAnimation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
                            stateMachine.sendEvent(Events.moveVertically);
                        });
                break;
            case LEFT:
                adjacentNodes
                        .stream()
                        .filter(node -> node.getX() < currentNode.getX() && node.getY() == currentNode.getY())
                        .findFirst()
                        .ifPresent(point2D -> {
                            translateAnimation = calculateTranslateTransition(currentNode, point2D);
                            translateAnimation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
                            stateMachine.sendEvent(Events.moveLeft);
                        });
                break;
            case RIGHT:
                adjacentNodes
                        .stream()
                        .filter(node -> node.getX() > currentNode.getX() && node.getY() == currentNode.getY())
                        .findFirst()
                        .ifPresent(point2D -> {
                            translateAnimation = calculateTranslateTransition(currentNode, point2D);
                            translateAnimation.setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
                            stateMachine.sendEvent(Events.moveRight);
                        });
                break;
        }
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

    @Override
    public void update(
            double deltaTimeSinceStart,
            double deltaTime,
            Set<KeyCode> input,
            Set<AnimatedSprite> nearbySprites
    ) {

        nearbySprites.forEach(animatedSprite -> {

            if (animatedSprite instanceof Spider
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())
            && stateMachine.getState().getId().equals(States.Awake)) {

                log.debug("Colliding with Spider!");
                stateMachine.sendEvent(Events.faint);
            }
        });
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug("timeToWakeUp: {}", stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }

}
