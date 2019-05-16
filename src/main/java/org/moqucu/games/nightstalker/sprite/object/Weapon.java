package org.moqucu.games.nightstalker.sprite.object;

import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.Map;
import java.util.Random;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@SuppressWarnings("unused")
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class Weapon extends AnimatedSprite {

    public class NoMoreRoundsException extends Exception {
    }

    enum States {TossedAway, ReappearedOnTheGround, PickedUp}

    enum Events {reappear, pickUp, tossAway}

    StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.TossedAway, Indices.builder().lower(0).upper(0).build(),
            States.ReappearedOnTheGround, Indices.builder().lower(0).upper(1).build(),
            States.PickedUp, Indices.builder().lower(0).upper(0).build()
    );

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty numberOfRounds = new SimpleIntegerProperty(6);

    private AudioClip pickUpGunSound = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/pickupgun.wav").toString()
    );

    private AudioClip shootSound
            = new AudioClip(Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/shoot.wav").toString());

    private int[][] randomGunPositions = {{9, 6}, {17, 3}, {18, 10}, {9, 3}, {3, 9}};

    private int randomIndex = (new Random()).nextInt(4);

    public Weapon() {

        super();
        relocate(randomGunPositions[randomIndex][0] * 32, randomGunPositions[randomIndex][1] * 32);
        setImage(new Image(translate("images/weapon.png")));
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
                .initial(States.TossedAway)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.TossedAway)
                .action(this::timeToReappearOnTheGround)
                .timerOnce(1000)
                .and()
                .withExternal()
                .source(States.TossedAway)
                .action(this::reappearedOnTheGround)
                .target(States.ReappearedOnTheGround)
                .event(Events.reappear)
                .and()
                .withExternal()
                .source(States.ReappearedOnTheGround)
                .target(States.PickedUp)
                .action(this::pickedUp)
                .event(Events.pickUp)
                .and()
                .withExternal()
                .source(States.PickedUp)
                .target(States.TossedAway)
                .action(this::tossedAway)
                .event(Events.tossAway);

        return builder.build();
    }

    private void timeToReappearOnTheGround(StateContext stateContext) {

        log.debug("timeToReappearOnTheGround: {}", stateContext);
        stateMachine.sendEvent(Events.reappear);
    }

    private void reappearedOnTheGround(StateContext stateContext) {

        log.debug("ReappearedOnTheGround: {}", stateContext);
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();
    }

    private void pickedUp(StateContext stateContext) {

        log.debug("PickedUp: {}", stateContext);
        numberOfRounds.set(6);
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();
    }

    private void tossedAway(StateContext stateContext) {

        log.debug("TossedAway: {}", stateContext);
        randomIndex = (new Random()).nextInt(4);
        relocate(randomGunPositions[randomIndex][0] * 32, randomGunPositions[randomIndex][1] * 32);
    }

    public void fire(Point2D currentLocation, Direction direction) throws NoMoreRoundsException {

        if (numberOfRounds.get() > 0) {

            numberOfRounds.set(numberOfRounds.get() - 1);
            shootSound.setVolume(0.1f);
            shootSound.play();

            getMaze().getAllBullets().stream().findAny().ifPresent(bullet -> bullet.shot(direction, currentLocation));

        } else
            throw new NoMoreRoundsException();
    }

    public void pickUp() {

        stateMachine.sendEvent(Events.pickUp);
    }

    public void tossAway() {

        stateMachine.sendEvent(Events.tossAway);
    }

    public int getNumberOfRounds() {

        return numberOfRounds.get();
    }

    public void setNumberOfRounds(int numberOfRounds) {

        this.numberOfRounds.set(numberOfRounds);
    }

    public IntegerProperty numberOfRoundsProperty() {

        return numberOfRounds;
    }
}
