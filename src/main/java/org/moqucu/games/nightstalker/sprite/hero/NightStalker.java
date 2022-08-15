package org.moqucu.games.nightstalker.sprite.hero;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import javafx.scene.image.Image;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.ManuallyMovableSprite;
import org.moqucu.games.nightstalker.sprite.enemy.GreyRobot;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.moqucu.games.nightstalker.sprite.object.RobotBullet;
import org.moqucu.games.nightstalker.sprite.object.Weapon;
import org.moqucu.games.nightstalker.sprite.enemy.Bat;
import org.moqucu.games.nightstalker.sprite.enemy.Spider;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.transition.TransitionKind;

import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends ManuallyMovableSprite implements Hittable {

    enum States {Inactive, Active, Paused, Dying, Stopped, Moving, Right, Left, Vertically, Fainting, Fainted}

    enum Events {spawn, moveRight, moveVertically, moveLeft, stop, faint, fallAsleep, die, becomeInactive, wakeUp}

    private StateMachine<States, Events> stateMachine;

    private MazeGraph mazeGraph;

    private Weapon weapon = null;

    private Direction direction;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty lives = new SimpleIntegerProperty(6);

    private AudioClip beingZappedSound = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/zap.wav").toString()
    );

    @SneakyThrows
    public NightStalker() {

        super();

        setImage(new Image(translate("images/night-stalker.png")));
        setAnimationProperties(
                Map.of
                        (
                                States.Inactive, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(24).upper(24).build())
                                        .build(),
                                States.Stopped, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.Vertically, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(1).upper(2).build())
                                        .build(),
                                States.Right, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(11).upper(18).build())
                                        .build(),
                                States.Left, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(3).upper(10).build())
                                        .build(),
                                States.Fainting, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(250)
                                        .frameIndices(Indices.builder().lower(19).upper(20).build())
                                        .build(),
                                States.Fainted, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(21).upper(21).build())
                                        .build(),
                                States.Dying, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(22).upper(23).build())
                                        .build()
                        )
        );

        setFocusTraversable(true);
        setOnKeyPressed(this::handleKeyPressedEvent);
        setOnKeyReleased(this::handleKeyReleasedEvent);

        stateMachine = buildStateMachine();

        //noinspection unchecked
        stateMachine.addStateListener(this);
        stateMachine.start();
    }

    @Override
    public void transitionEnded(Transition transition) {

        super.transitionEnded(transition);
        switch ((States)transition.getTarget().getId()) {

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
                moveMeFromStart();
                break;
            case Fainting:
                stopMovingMe();
                log.debug("I am still fainting...");
                break;
            case Dying:
                if (transition.getKind().equals(TransitionKind.EXTERNAL)) {

                    stopMovingMe();
                    log.debug("I am still dying...");
                    lives.setValue(lives.getValue()-1);
                    log.debug("Lives left: {}", lives.get());
                    beingZappedSound.play();
                }
                break;
        }
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
                .timerOnce(500)
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
                .timerOnce(500)
                .and()
                .withExternal()
                .source(States.Fainting)
                .target(States.Fainted)
                .event(Events.fallAsleep)
                .and()
                .withInternal()
                .source(States.Fainted)
                .action(stateContext -> stateMachine.sendEvent(Events.wakeUp))
                .timerOnce(500)
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
                .timerOnce(1750)
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
                    (new ClassPathResource("maze-graph-night-stalker.json")
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
            stateMachine.sendEvent(Events.die);
        } else if (collidableObject instanceof RobotBullet) {

            log.debug("Hit by bullet...");
            stateMachine.sendEvent(Events.die);
        }
    }

    @Override
    public boolean isHittable() {

        return stateMachine.getState().getId() != States.Dying;
    }

    @SuppressWarnings("unused")
    public double getLives() {

        return lives.get();
    }

    @SuppressWarnings("unused")
    public void setLivest(int lives) {

        this.lives.set(lives);
    }

    @SuppressWarnings("unused")
    public IntegerProperty livesProperty() {

        return lives;
    }
}
