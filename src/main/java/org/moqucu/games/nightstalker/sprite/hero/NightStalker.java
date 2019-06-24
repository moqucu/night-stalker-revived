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
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.ManuallyMovableSprite;
import org.moqucu.games.nightstalker.sprite.enemy.GreyRobot;
import org.moqucu.games.nightstalker.sprite.object.Weapon;
import org.moqucu.games.nightstalker.sprite.enemy.Bat;
import org.moqucu.games.nightstalker.sprite.enemy.Spider;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends ManuallyMovableSprite implements Hittable {

    enum States {Inactive, Active, Paused, Dying, Stopped, Moving, Right, Left, Vertically, Fainting, Fainted}

    enum Events {spawn, moveRight, moveVertically, moveLeft, stop, faint, fallAsleep, die, becomeInactive, wakeUp}

    private StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Inactive, Indices.builder().lower(24).upper(24).build(),
            States.Stopped, Indices.builder().lower(0).upper(0).build(),
            States.Vertically, Indices.builder().lower(1).upper(2).build(),
            States.Right, Indices.builder().lower(11).upper(18).build(),
            States.Left, Indices.builder().lower(3).upper(10).build(),
            States.Fainting, Indices.builder().lower(19).upper(20).build(),
            States.Fainted, Indices.builder().lower(21).upper(21).build(),
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

                log.debug("My state changed to {}", transition.getTarget().getId());
                if (frameBoundaries.containsKey(transition.getTarget().getId()))
                    setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));

                switch (transition.getTarget().getId()) {

                    case Inactive:
                        translateXProperty().set(spawnCoordinateXProperty().get());
                        translateYProperty().set(spawnCoordinateYProperty().get());
                         break;
                    case Stopped:
                        stopMovingMe();
                        stopAnimatingMe();
                        break;
                    case Left:
                    case Right:
                    case Vertically:
                        animateMeFromStart();
                        moveMeFromStart();
                        break;
                    case Fainting:
                        log.debug("I am still fainting...");
                        animateMeFromStart();
                        break;
                    case Dying:
                        log.debug("I am still dying...");
                        animateMeFromStart();
                        beingZappedSound.play();
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
                .initial(States.Inactive)
                .state(States.Inactive)
                .state(States.Active)
                .state(States.Paused)
                .state(States.Dying)
                .and()
                .withStates()
                .parent(States.Active)
                .initial(States.Stopped)
                .state(States.Stopped)
                .state(States.Moving)
                .and()
                .withStates()
                .parent(States.Moving)
                .initial(States.Right)
                .state(States.Right)
                .state(States.Left)
                .state(States.Vertically)
                .and()
                .withStates()
                .parent(States.Paused)
                .initial(States.Fainting)
                .state(States.Fainting)
                .state(States.Fainted)
        ;
        builder.configureTransitions()
                .withInternal()
                .source(States.Inactive)
                .action(stateContext -> stateMachine.sendEvent(Events.spawn))
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.Inactive)
                .target(States.Stopped)
                .event(Events.spawn)
                .and()
                .withExternal()
                .source(States.Stopped)
                .target(States.Right)
                .event(Events.moveRight)
                .and()
                .withExternal()
                .source(States.Stopped)
                .target(States.Left)
                .event(Events.moveLeft)
                .and()
                .withExternal()
                .source(States.Stopped)
                .target(States.Vertically)
                .event(Events.moveVertically)
                .and()
                .withExternal()
                .source(States.Moving)
                .target(States.Stopped)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Active)
                .target(States.Fainting)
                .event(Events.faint)
                .and()
                .withInternal()
                .source(States.Fainting)
                .action(stateContext -> stateMachine.sendEvent(Events.fallAsleep))
                .timerOnce(2000)
                .and()
                .withExternal()
                .source(States.Fainting)
                .target(States.Fainted)
                .event(Events.fallAsleep)
                .and()
                .withExternal()
                .source(States.Fainted)
                .target(States.Stopped)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Active)
                .target(States.Dying)
                .event(Events.die)
                .and()
                .withExternal()
                .source(States.Paused)
                .target(States.Dying)
                .event(Events.die)
                .and()
                .withInternal()
                .source(States.Dying)
                .action(stateContext -> stateMachine.sendEvent(Events.becomeInactive))
                .timerOnce(2000)
                .and()
                .withExternal()
                .source(States.Dying)
                .target(States.Inactive)
                .event(Events.becomeInactive)
        ;

        return builder.build();
    }

    private void handleKeyReleasedEvent(KeyEvent keyEvent) {

        log.trace("The {} key was released on me.", keyEvent.getCode());

        stateMachine.sendEvent(Events.stop);
    }

    private void handleKeyPressedEvent(KeyEvent keyEvent) {

        log.trace("The {} key was pressed on me.", keyEvent.getCode());

        if (stateMachine.getState().getId() != States.Active)
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
    public void hitBy(Collidable collidableObject) {

        if (collidableObject instanceof Spider || collidableObject instanceof Bat) {

            log.debug("I collided with an animal, fainting...");
            stateMachine.sendEvent(Events.faint);
        } else if (collidableObject instanceof Weapon) {

            log.debug("I collided with a weapon, picking it up...");
            this.weapon = (Weapon) collidableObject;
            weapon.pickUp();
        } else if (collidableObject instanceof GreyRobot) {

            log.debug("I collided with a robot, dying...");
            stopMovingMe();
            stateMachine.sendEvent(Events.die);
        }
    }

    @Override
    public boolean isHittable() {

        return stateMachine.getState().getId() != States.Dying;
    }
}
