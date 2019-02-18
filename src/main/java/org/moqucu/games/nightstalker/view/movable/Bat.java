package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.Sprite;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.*;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends ArtificiallyMovedSprite {

    enum States {
        asleep, awake
    }

    enum Events {
        wakeUp, sleep
    }

    private int numberOfFrames = 6;
    private final Rectangle2D[] cellClips = new Rectangle2D[numberOfFrames];

    private Animation animation;
    private final IntegerProperty frameCounter = new SimpleIntegerProperty(1);

    StateMachine<States, Events> stateMachine;

    private boolean awake = false;

    private double sleepTime;

    public Bat() {

        super(new Point2D(17 * WIDTH - WIDTH / 2, 3 * HEIGHT));

        this.sleepTime = 5.0;
        setVelocity(35);

        setImage(new Image(translate("images/bat.png")));
        for (int i = 0; i < numberOfFrames; i++)
            cellClips[i] = new Rectangle2D(i * WIDTH, 0, WIDTH, HEIGHT);
        setViewport(cellClips[0]);

        stateMachine = buildStateMachine();
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
                .timerOnce(2500)
                .and()
                .withExternal()
                .source(States.asleep)
                .action(this::wokeUp)
                .target(States.awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.awake)
                .target(States.asleep)
                .event(Events.sleep);

        return builder.build();
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug(stateContext);
        stateMachine.sendEvent(Events.wakeUp);
    }

    private void wokeUp(StateContext stateContext) {

        log.debug(stateContext);
        animation = new Transition() {
            {
                setCycleDuration(Duration.millis(500));
                setCycleCount(INDEFINITE);
                setAutoReverse(true);
                play();
            }

            protected void interpolate(double frac) {
                setViewport(cellClips[Math.round((numberOfFrames - 2) * (float) frac) + 1]);
            }

        };
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        if (deltaTimeSinceStart < sleepTime)
            return;
        else
            awake = true;


        super.update(deltaTimeSinceStart, deltaTime, input, sprites);
    }
}
