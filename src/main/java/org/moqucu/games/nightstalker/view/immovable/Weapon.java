package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;
import java.util.Random;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@SuppressWarnings("unused")
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class Weapon extends AnimatedSprite {

    public class NoMoreRoundsException extends Exception {
    }

    enum States {
        tossedAway, reappearedOnTheGround, pickedUp
    }

    enum Events {
        reappear, pickUp, tossAway
    }

    StateMachine<States, Events> stateMachine;

    private byte rounds = 6;

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
        setFrameDurationInMillis(200);
        setAutoReversible(3);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

            }
        });
        stateMachine.start();

    }

    @SneakyThrows
    private StateMachine<States, Events> buildStateMachine() {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.tossedAway)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.tossedAway)
                .action(this::timeToReappearOnTheGround)
                .timerOnce(1500)
                .and()
                .withExternal()
                .source(States.tossedAway)
                .action(this::reappearedOnTheGround)
                .target(States.reappearedOnTheGround)
                .event(Events.reappear)
                .and()
                .withExternal()
                .source(States.reappearedOnTheGround)
                .target(States.pickedUp)
                .action(this::pickedUp)
                .event(Events.pickUp)
                .and()
                .withExternal()
                .source(States.pickedUp)
                .target(States.tossedAway)
                .action(this::tossedAway)
                .event(Events.tossAway);

        return builder.build();
    }

    private void timeToReappearOnTheGround(StateContext stateContext) {

        log.debug("timeToReappearOnTheGround: {}", stateContext);
        stateMachine.sendEvent(Events.reappear);
    }

    private void reappearedOnTheGround(StateContext stateContext) {

        log.debug("reappearedOnTheGround: {}", stateContext);
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();
        playAnimation();
    }

    private void pickedUp(StateContext stateContext) {

        log.debug("pickedUp: {}", stateContext);
        rounds = 6;
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();
        stopAnimation();
    }

    private void tossedAway(StateContext stateContext) {

        log.debug("tossedAway: {}", stateContext);
        randomIndex = (new Random()).nextInt(4);
        setCurrentCoordinates(
                new Point2D(
                        randomGunPositions[randomIndex][0] * WIDTH,
                        randomGunPositions[randomIndex][1] * HEIGHT
                )
        );
    }

    public void fire() throws NoMoreRoundsException {

        if (rounds > 0) {

            rounds--;
            shootSound.setVolume(0.1f);
            shootSound.play();
        } else
            throw new NoMoreRoundsException();
    }
}
