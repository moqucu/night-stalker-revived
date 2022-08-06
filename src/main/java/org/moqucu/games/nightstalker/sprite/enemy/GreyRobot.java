package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.Approachable;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.Sprite;
import org.moqucu.games.nightstalker.sprite.object.Bullet;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartOne;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartTwo;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.transition.TransitionKind;

import java.util.Map;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends SleepingSprite implements Hittable, Collidable, Approachable {

    private enum States {Inactive, Active, Stopped, Moving, SlowlyMoving, MovingFast, FallingApart}

    private enum Events {spawn, move, faster, stop, fallApart, becomeInactive}

    private StateMachine<States, Events> stateMachine;

    private Group partsAnimationGroup;
    private GreyRobotPartOne partOne;
    private GreyRobotPartTwo partTwo;

    private CubicCurve curve = new CubicCurve();

    private AudioClip destruction = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/destruct.wav").toString()
    );

    private Point2D bezier(double t, Point2D... points) {
        if (points.length == 2) {
            return points[0].multiply(1 - t).add(points[1].multiply(t));
        }
        Point2D[] leftArray = new Point2D[points.length - 1];
        System.arraycopy(points, 0, leftArray, 0, points.length - 1);
        Point2D[] rightArray = new Point2D[points.length - 1];
        System.arraycopy(points, 1, rightArray, 0, points.length - 1);
        return bezier(t, leftArray).multiply(1 - t).add(bezier(t, rightArray).multiply(t));
    }

    private Point2D bezierDeriv(double t, Point2D... points) {
        if (points.length == 2) {
            return points[1].subtract(points[0]);
        }
        Point2D[] leftArray = new Point2D[points.length - 1];
        System.arraycopy(points, 0, leftArray, 0, points.length - 1);
        Point2D[] rightArray = new Point2D[points.length - 1];
        System.arraycopy(points, 1, rightArray, 0, points.length - 1);
        return bezier(t, leftArray).multiply(-1).add(bezierDeriv(t, leftArray).multiply(1 - t))
                .add(bezier(t, rightArray)).add(bezierDeriv(t, rightArray).multiply(t));
    }

    public GreyRobot() {

        super();

        setImage(new Image(translate("images/grey-robot.png")));
        setAnimationProperties(
                Map.of
                        (
                                States.Inactive, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(10).upper(10).build())
                                        .build(),
                                States.Stopped, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(0).build())
                                        .build(),
                                States.SlowlyMoving, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(1).build())
                                        .build(),
                                States.MovingFast, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(0).upper(1).build())
                                        .build(),
                                States.FallingApart, AnimationProperty.builder()
                                        .autoReversible(false)
                                        .frameDurationInMillis(100)
                                        .frameIndices(Indices.builder().lower(10).upper(10).build())
                                        .build()
                        )
        );

        stateMachine = buildStateMachine();

        //noinspection unchecked
        stateMachine.addStateListener(this);
        setOnFinished(actionEvent -> stateMachine.sendEvent(Events.stop));
        stateMachine.start();
    }

    @SneakyThrows
    private StateMachine<GreyRobot.States, GreyRobot.Events> buildStateMachine() {

        StateMachineBuilder.Builder<GreyRobot.States, GreyRobot.Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.Inactive)
                .state(States.Active)
                .state(States.FallingApart)
                .and()
                .withStates()
                .parent(States.Active)
                .initial(States.Stopped)
                .state(States.Moving)
                .and()
                .withStates()
                .parent(States.Moving)
                .initial(States.SlowlyMoving)
                .state(States.MovingFast);

        builder.configureTransitions()
                .withInternal()
                .source(States.Inactive)
                .action(stateContext -> stateMachine.sendEvent(Events.spawn))
                .timerOnce(500)
                .and()
                .withExternal()
                .source(States.Inactive)
                .event(Events.spawn)
                .target(States.Stopped)
                .and()
                .withExternal()
                .source(States.Stopped)
                .event(Events.move)
                .target(States.SlowlyMoving)
                .and()
                .withInternal()
                .source(States.SlowlyMoving)
                .action(stateContext -> stateMachine.sendEvent(Events.faster))
                .timerOnce(4000)
                .and()
                .withExternal()
                .source(States.SlowlyMoving)
                .event(Events.faster)
                .target(States.MovingFast)
                .event(Events.move)
                .and()
                .withExternal()
                .source(States.Moving)
                .event(Events.stop)
                .target(States.Stopped)
                .and()
                .withExternal()
                .source(States.Active)
                .event(Events.fallApart)
                .target(States.FallingApart)
                .and()
                .withInternal()
                .source(States.FallingApart)
                .action(this::fallApart)
                .timerOnce(getSleepTimeInMillis())
                .and()
                .withExternal()
                .source(States.FallingApart)
                .event(Events.becomeInactive)
                .target(States.Inactive);

        return builder.build();
    }

    @Override
    public void transitionEnded(Transition transition) {

        super.transitionEnded(transition);

        switch ((States) transition.getTarget().getId()) {

            case Inactive:
                translateXProperty().set(spawnCoordinateXProperty().get());
                translateYProperty().set(spawnCoordinateYProperty().get());
                break;
            case Stopped:
                stopMovingMe();
                stateMachine.sendEvent(Events.move);
                break;
            case SlowlyMoving:
                if (transition.getKind().equals(TransitionKind.EXTERNAL)) {

                    computeNextMoveAnimationBasedOnRandomDirection();
                    moveMeFromStart();
                }
                break;
            case MovingFast:
                setVelocity(50);
                break;
            case FallingApart:
                stopMovingMe();
                break;
        }

    }

    @Override
    public void hitBy(Collidable collidableObject) {

        if (collidableObject instanceof Bullet) {

            log.debug("Hit by bullet...");
            stopMovingMe();
            notifyHitListenerAboutHit();
            stateMachine.sendEvent(Events.fallApart);
        }
    }

    private void fallApart(StateContext stateContext) {

        log.trace("fallApart: {}", stateContext);
        destruction.setVolume(0.1f);
        destruction.play();

        curve.setStartX(getX());
        curve.setStartY(getY());

        curve.setControlX1(getX() + 50);
        curve.setControlY1(getY() - 200);
        curve.setControlX2(getX() + 100);
        curve.setControlY2(getY() - 150);
        curve.setEndX(getX() + 150);
        curve.setEndY(getY() + 200);
        curve.setStroke(Color.TRANSPARENT);
        curve.setStrokeWidth(1);
        curve.setFill(Color.TRANSPARENT);

        javafx.animation.Transition transition = new javafx.animation.Transition() {

            {
                setCycleDuration(Duration.millis(2000));
            }

            @Override
            protected void interpolate(double frac) {
                Point2D start = new Point2D(curve.getStartX(), curve.getStartY());
                Point2D control1 = new Point2D(curve.getControlX1(), curve.getControlY1());
                Point2D control2 = new Point2D(curve.getControlX2(), curve.getControlY2());
                Point2D end = new Point2D(curve.getEndX(), curve.getEndY());

                Point2D center = bezier(frac, start, control1, control2, end);

                double width = partOne.getBoundsInLocal().getWidth();
                double height = partOne.getBoundsInLocal().getHeight();

                partOne.setTranslateX(center.getX() - width / 2);
                partOne.setTranslateY(center.getY() - height / 2);

                Point2D tangent = bezierDeriv(frac, start, control1, control2, end);
                double angle = Math.toDegrees(Math.atan2(tangent.getY(), tangent.getX()));
                // rectPath.setRotate(angle);
            }

        };

        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();

        //   this.getParent().getC
        // partOne.startFlying(this.getDirection(), this.getCurrentLocation());
        // partTwo.startFlying(this.getDirection(), this.getCurrentLocation());

        stateMachine.sendEvent(Events.becomeInactive);
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

        return stateMachine.getState().getId().equals(States.Active);
    }

    @Override
    public void approachedBy(Set<Sprite> sprite) {

        sprite.forEach(log::debug);
    }

    @Override
    public Line getLineOfSight() {

        Point2D currentNode = getCurrentLocation();
        Direction direction = getDirection();
        if (currentNode == null)
            log.error("current node is null!");
        if (direction == null)
            log.error("direction is null!");
        Point2D furthestReachableNode = getEnemyMazeGraph().getFurthestReachableNode(currentNode, direction);

        return new Line(
                currentNode.getX(),
                currentNode.getY(),
                furthestReachableNode.getX(),
                furthestReachableNode.getY()
        );
    }
}
