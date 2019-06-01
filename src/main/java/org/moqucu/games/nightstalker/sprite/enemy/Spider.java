package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.SpawnableSprite;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
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
public class Spider extends SpawnableSprite implements Hittable {

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
            public void transitionEnded(org.springframework.statemachine.transition.Transition<States, Events> transition) {

                log.debug("State changed to {}", transition.getTarget().getId());

                switch (transition.getTarget().getId()) {

                    case Awake:
                        computeNextMoveAnimationBasedOnRandomDirection();
                        Point2D deltaNode = getNextNode().subtract(getPreviousNode());
                        if (deltaNode.getX() != 0)
                            stateMachine.sendEvent(Events.moveHorizontally);
                        else if (deltaNode.getY() != 0)
                            stateMachine.sendEvent(Events.moveVertically);
                        break;
                    case Asleep:
                    case MovingHorizontally:
                    case MovingVertically:
                    case Hit:
                        setFrameIndices(frameBoundaries.get(transition.getTarget().getId()));
                        moveMeFromStart();
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
                .initial(States.Asleep)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.Asleep)
                .action(this::timeToWakeUp)
                .timerOnce(500)
                .and()
                .withExternal()
                .source(States.Asleep)
                .target(States.Awake)
                .event(Events.wakeUp)
                .and()
                .withExternal()
                .source(States.Awake)
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

    private void timeToRelocateAndFallAsleep(StateContext<States, Events> statesEventsStateContext) {

        log.debug("timeToRelocateAndFallAsleep: {}", statesEventsStateContext);
        this.translateXProperty().set(this.spawnCoordinateXProperty().get());
        this.translateYProperty().set(this.spawnCoordinateYProperty().get());
        stateMachine.sendEvent(Events.spawn);
    }

    private void timeToWakeUp(StateContext stateContext) {

        log.debug("timeToWakeUp: {}", stateContext);
        animateMeFromStart();
        stateMachine.sendEvent(Events.wakeUp);
    }

    @Override
    public void detectCollision(Set<AnimatedSprite> nearbySprites) {

        nearbySprites.forEach(animatedSprite -> {

            if ((animatedSprite instanceof Bullet)
                    && this.getBoundsInParent().intersects(animatedSprite.getBoundsInParent())
                    && ((Bullet) animatedSprite).isHittable()) {

                log.debug("Hit by bullet...");
                stopMovingMe();
                stateMachine.sendEvent(Events.hit);
            }
        });
    }

    @Override
    public boolean isHit() {

        return stateMachine.getState().getId().equals(States.Hit);
    }
}
