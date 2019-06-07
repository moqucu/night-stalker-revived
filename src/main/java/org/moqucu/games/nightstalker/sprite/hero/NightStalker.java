package org.moqucu.games.nightstalker.sprite.hero;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.ManuallyMovableSprite;
import org.moqucu.games.nightstalker.sprite.enemy.GreyRobot;
import org.moqucu.games.nightstalker.sprite.object.Weapon;
import org.moqucu.games.nightstalker.sprite.enemy.Bat;
import org.moqucu.games.nightstalker.sprite.enemy.Spider;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends ManuallyMovableSprite implements Hittable {

    enum States {Alive, Stopped, MovingLeft, MovingRight, MovingVertically, Fainting, Dying}

    enum Events {moveLeft, moveRight, moveVertically, stop, faint, wakeUp, die}

    private StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Alive, Indices.builder().lower(0).upper(0).build(),
            States.MovingVertically, Indices.builder().lower(1).upper(2).build(),
            States.MovingRight, Indices.builder().lower(11).upper(18).build(),
            States.MovingLeft, Indices.builder().lower(3).upper(10).build(),
            States.Fainting, Indices.builder().lower(19).upper(21).build(),
            States.Dying, Indices.builder().lower(22).upper(23).build()
    );

    private MazeGraph mazeGraph;

    private Weapon weapon = null;

    private Direction direction;

    private AudioClip beingZappedSound = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/zap.wav").toString()
    );

    @SneakyThrows
    public NightStalker() {

        super();

        setImage(new Image(translate("images/night-stalker.png")));

        setFocusTraversable(true);
        setOnKeyPressed(this::handleKeyPressedEvent);
        setOnKeyReleased(this::handleKeyReleasedEvent);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                log.debug("State changed to {}", transition.getTarget().getId());

                switch (transition.getTarget().getId()) {

                    case Alive:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        stopMovingMe();
                        stopAnimatingMe();
                        break;
                    case MovingLeft:
                    case MovingRight:
                    case MovingVertically:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        animateMeFromStart();
                        moveMeFromStart();
                        break;
                    case Fainting:
                        log.debug("fainting...");
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        animateMeFromStart();
                        break;
                }
            }
        });
        stateMachine.start();
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Alive)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.Alive)
                .target(States.MovingLeft)
                .event(Events.moveLeft)
                .and()
                .withExternal()
                .source(States.MovingLeft)
                .target(States.Alive)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Alive)
                .target(States.MovingRight)
                .event(Events.moveRight)
                .and()
                .withExternal()
                .source(States.Alive)
                .target(States.Fainting)
                .event(Events.faint)
                .and()
                .withInternal()
                .source(States.Fainting)
                .action(this::timeToWakeUp)
                .timerOnce(3000)
                .and()
                .withExternal()
                .source(States.Fainting)
                .target(States.Alive)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.MovingRight)
                .target(States.Alive)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Alive)
                .target(States.MovingVertically)
                .event(Events.moveVertically)
                .and()
                .withExternal()
                .source(States.MovingVertically)
                .target(States.Alive)
                .event(Events.stop)
                .and()
                .withInternal()
                .source(States.Dying)
                .action(this::dying)
                .timerOnce(2000);

        return builder.build();
    }

    private void handleKeyReleasedEvent(KeyEvent keyEvent) {

        log.debug("Key released: {}", keyEvent.getCode());

        stateMachine.sendEvent(Events.stop);
    }

    private void handleKeyPressedEvent(KeyEvent keyEvent) {

        log.debug("Key pressed: {}", keyEvent.getCode());

        if (stateMachine.getState().getId() != States.Alive)
            return;

        switch (keyEvent.getCode()) {

            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                setDirectionBasedOnKeyEventCode(keyEvent.getCode());
                computePathTransitionBasedOnDirection(getDirection());
                translateKeyCodeDirectionToStateMachineEvent(keyEvent.getCode());
                break;
            case Q:
                System.exit(0);
            case SPACE:
                if (weapon != null)
                    try {
                        weapon.fire(getCurrentLocation(), getDirection());
                    } catch (Weapon.NoMoreRoundsException e) {
                        weapon.tossAway();
                    }
        }
    }

    private void setDirectionBasedOnKeyEventCode(KeyCode keyEventCode) {

        switch (keyEventCode) {

            case UP:
                direction = Direction.Up;
                break;
            case DOWN:
                direction = Direction.Down;
                break;
            case LEFT:
                direction = Direction.Left;
                break;
            case RIGHT:
                direction = Direction.Right;
                break;
        }
    }

    private void translateKeyCodeDirectionToStateMachineEvent(KeyCode keyCode) {

        switch (keyCode) {

            case UP:
            case DOWN:
                stateMachine.sendEvent(Events.moveVertically);
                break;
            case LEFT:
                stateMachine.sendEvent(Events.moveLeft);
                break;
            case RIGHT:
                stateMachine.sendEvent(Events.moveRight);
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
    public void detectCollision(Set<AnimatedSprite> nearbySprites) {

        nearbySprites.forEach(animatedSprite -> {

            if ((animatedSprite instanceof Spider || animatedSprite instanceof Bat)
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())
                    && stateMachine.getState().getId() != States.Fainting
                    && !((Hittable) animatedSprite).isHit()) {

                log.debug("Colliding with animal.");
                stateMachine.sendEvent(Events.stop);
                stateMachine.sendEvent(Events.faint);
            } else if (animatedSprite instanceof Weapon
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())) {

                this.weapon = (Weapon) animatedSprite;
                weapon.pickUp();
            } else if (animatedSprite instanceof GreyRobot
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())
                    && !((Hittable) animatedSprite).isHit()) {

                log.debug("Colliding with animal.");
                stateMachine.sendEvent(Events.stop);
                stateMachine.sendEvent(Events.die);
            }
        });
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug("timeToWakeUp: {}", stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }


    private void dying(StateContext stateContext) {

        log.debug("I am dying: {}", stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }

    @Override
    public boolean isHit() {

        return false;
    }
}
