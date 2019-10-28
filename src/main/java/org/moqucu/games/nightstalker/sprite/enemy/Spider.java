package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;
import java.util.Map;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class Spider extends SleepingSprite implements Hittable, Collidable {

    private enum States {Asleep, Awake, MovingHorizontally, MovingVertically, Hit}

    private enum Events {wakeUp, moveHorizontally, moveVertically, stop, hit, spawn}

    private StateMachine<States, Events> stateMachine;

    private Map<States, Indices> frameBoundaries = Map.of(
            States.Asleep, Indices.builder().lower(0).upper(0).build(),
            States.Awake, Indices.builder().lower(0).upper(0).build(),
            States.MovingVertically, Indices.builder().lower(0).upper(1).build(),
            States.MovingHorizontally, Indices.builder().lower(2).upper(3).build(),
            States.Hit, Indices.builder().lower(4).upper(7).build()
    );

    public Spider() {

        super();

        setImage(new Image(translate("images/spider.png")));
        setAutoReversible(false);
        setFrameDurationInMillis(50);
        setVelocity(35);

        stateMachine = buildStateMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<>() {

            @Override
            public void transitionEnded(Transition<States, Events> transition) {

                log.debug("State changed to {}", transition.getTarget().getId());
                setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));

                if (transition.getTarget().getId() == States.Awake)
                    wokeUp(transition);
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
                .initial(States.Asleep)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.Asleep)
                .action(this::timeToWakeUp)
                .timerOnce(getSleepTimeInMillis())
                .and()
                .withExternal()
                .source(States.Asleep)
                .target(States.Awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Awake)
                .action(this::startedToMove)
                .target(States.MovingHorizontally)
                .event(Events.moveHorizontally)
                .and()
                .withExternal()
                .source(States.MovingHorizontally)
                .target(States.Awake)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.Awake)
                .action(this::startedToMove)
                .target(States.MovingVertically)
                .event(Events.moveVertically)
                .and()
                .withExternal()
                .source(States.MovingVertically)
                .target(States.Awake)
                .event(Events.stop)
                .and()
                .withExternal()
                .source(States.MovingVertically)
                .target(States.Hit)
                .event(Events.hit)
                .and()
                .withExternal()
                .source(States.MovingHorizontally)
                .target(States.Hit)
                .event(Events.hit)
                .and()
                .withInternal()
                .source(States.Hit)
                .action(this::timeToRelocateAndFallAsleep)
                .timerOnce(getSpawnTimeInMillis())
                .and()
                .withExternal()
                .source(States.Hit)
                .target(States.Asleep)
                .event(Events.spawn)
        ;

        return builder.build();
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.trace("timeToWakeUp: {}", stateContext);

        animateMeFromStart();
        stateMachine.sendEvent(Events.wakeUp);
    }

    private void wokeUp(Transition<States, Events> transition) {

        log.trace("wokeUp: {}", transition);

        log.debug("Woke up, with current location = {}", getCurrentLocation());

        computeNextMoveAnimationBasedOnRandomDirection();
        log.debug("Animation computed, next node = {}", getNextNode());

        Point2D deltaNode = getNextNode().subtract(getPreviousNode());
        if (deltaNode.getX() != 0) {

            log.debug("Looks like I need to move horizontally...");
            stateMachine.sendEvent(Events.moveHorizontally);
        } else if (deltaNode.getY() != 0) {

            log.debug("Looks like I need to move vertically...");
            stateMachine.sendEvent(Events.moveVertically);
        } else
            log.debug("Not clear where to move to based on deltaNode = {}?", deltaNode);
    }

    private void startedToMove(StateContext stateContext) {

        log.debug("startedToMove: {}", stateContext);
        moveMeFromStart();
    }

    private void timeToRelocateAndFallAsleep(StateContext<States, Events> statesEventsStateContext) {

        log.debug("timeToRelocateAndFallAsleep: {}", statesEventsStateContext);
        this.translateXProperty().set(this.spawnCoordinateXProperty().get());
        this.translateYProperty().set(this.spawnCoordinateYProperty().get());
        stateMachine.sendEvent(Events.spawn);
    }

    @Override
    public void hitBy(Collidable collidableObject) {

        if (collidableObject instanceof Bullet) {

            log.debug("Hit by bullet...");
            stopMovingMe();
            notifyHitListenerAboutHit();
            stateMachine.sendEvent(Events.hit);
        }
    }

    @Override
    public boolean isCollidable() {

        return isActive();
    }

    @Override
    public boolean isHittable() {

        return isActive();
    }

    private boolean isActive() {
        switch (stateMachine.getState().getId()) {
            case MovingVertically:
            case MovingHorizontally:
            case Asleep:
            case Awake:
                return true;
            default:
                return false;
        }
    }
}
