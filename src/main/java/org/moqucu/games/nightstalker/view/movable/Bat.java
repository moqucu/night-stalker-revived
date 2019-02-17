package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import org.moqucu.games.nightstalker.view.Sprite;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.*;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends ArtificiallyMovedSprite {

    enum States {
        asleep, awake
    }

    enum Events {
        wakeUp, sleep
    }

    private int numCells = 6;
    private final Rectangle2D[] cellClips = new Rectangle2D[numCells];

    private final Timeline timeline;
    private final IntegerProperty frameCounter = new SimpleIntegerProperty(1);

    StateMachine<States, Events> stateMachine;

    private boolean awake = false;

    private double sleepTime;

    public Bat() {

        super(new Point2D(17 * WIDTH - WIDTH / 2, 3 * HEIGHT));

        this.sleepTime = 5.0;
        setVelocity(35);

        setImage(new Image(translate("images/bat.png")));
        for (int i = 0; i < numCells; i++)
            cellClips[i] = new Rectangle2D(i * 32, 0, 32, 32);
        setViewport(cellClips[0]);
        timeline = new Timeline(
                new KeyFrame(new Duration(100), event -> {

                    if (stateMachine.getState().getId() == States.awake) {
                        frameCounter.set((frameCounter.get() + 1) % numCells);
                        setViewport(cellClips[frameCounter.get()]);
                    }
                })
        );
        frameCounter.set(1);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.playFromStart();

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
                .action(stateContext -> stateMachine.sendEvent(Events.wakeUp))
                .timerOnce(2500)
                .and()
                .withExternal()
                .source(States.asleep)
                .target(States.awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.awake).target(States.asleep)
                .event(Events.sleep);

        return builder.build();
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
