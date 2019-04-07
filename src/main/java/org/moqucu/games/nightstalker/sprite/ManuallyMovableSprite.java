package org.moqucu.games.nightstalker.sprite;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ManuallyMovableSprite extends MovableSprite {

    private static final double MAX_OFFSET = 8.0;

    private Path path = new Path();

    Duration duration = Duration.ZERO;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final DoubleProperty maxOffset = new SimpleDoubleProperty(MAX_OFFSET);

    @SneakyThrows
    protected ManuallyMovableSprite() {

        super();
        moveAnimation = new PathTransition();
        moveAnimation.setCycleCount(1);
        getMoveAnimation().setNode(this);
        getMoveAnimation().setInterpolator(Interpolator.LINEAR);
    }

    protected void setOnFinished(EventHandler<ActionEvent> finishedActionEventHandler) {

        moveAnimation.setOnFinished(finishedActionEventHandler);
    }

    protected void computePathTransitionBasedOnDirection(KeyCode direction) {

        Point2D startingPoint = getCurrentNode();

        resetPath();

        movePathToStartingPointAndResetDuration(startingPoint);
        log.debug("Starting point: {}", startingPoint);

        Point2D furthestReachableNodeInDirection = getMazeGraph().getFurthestReachableNode(startingPoint, direction);
        log.debug("Initial value for furthest reachable startingPoint: {}", furthestReachableNodeInDirection);

        if (roundedPointsRepresentSameScreenLocation(startingPoint, furthestReachableNodeInDirection)) {

            log.debug("No direct movement possible, checking if turning around nearby corner is possible...");

            Map<Double, Point2D> distanceToClosestNeighboringNodes
                    = findClosestNeighboringNodesAndOrganizeByDistance(startingPoint, direction);
            log.debug(
                    "Found {} points (including starting point) that qualify as direct neighbors.",
                    distanceToClosestNeighboringNodes.keySet().size()
            );

            Point2D closestPoint = determineClosestPointInDirection(direction, distanceToClosestNeighboringNodes);
            log.debug("Closest point to starting position: {}", closestPoint);

            if (!closestPoint.equals(startingPoint)) {

                log.debug("Found a real neighbor, checking if from there movement to direction is possible...");

                if (nodeSupportsMovementToDirection(closestPoint, direction)) {

                    linePathTo(closestPoint);
                    Point2D finalPoint = getMazeGraph().getFurthestReachableNode(closestPoint, direction);
                    linePathTo(finalPoint);

                    log.debug("Found alternative path via {} to {}.", closestPoint, finalPoint);
                }
            }
        } else
            linePathTo(furthestReachableNodeInDirection);

        updateMoveAnimationWithNewPathAndDuration();
    }

    private void updateMoveAnimationWithNewPathAndDuration() {

        getMoveAnimation().setPath(path);
        getMoveAnimation().setDuration(duration);
    }

    private boolean nodeSupportsMovementToDirection(Point2D node, KeyCode direction) {

        return !roundedPointsRepresentSameScreenLocation(
                node,
                getMazeGraph().getFurthestReachableNode(node, direction)
        );
    }

    private Map<Double, Point2D> findClosestNeighboringNodesAndOrganizeByDistance(
            Point2D startingPoint,
            KeyCode direction
    ) {

        Map<Double, Point2D> distanceToClosestNeighboringNodes = new HashMap<>(2);

        distanceToClosestNeighboringNodes.put(Double.MAX_VALUE, startingPoint);

        determineOrthogonalKeyCodesFor(direction).forEach(keyCode -> {

            Point2D closedNodeInDirection = getMazeGraph().getClosestReachableNode(
                    startingPoint,
                    keyCode,
                    maxOffset.get()
            );
            distanceToClosestNeighboringNodes.put(
                    startingPoint.distance(closedNodeInDirection),
                    closedNodeInDirection
            );
        });

        return distanceToClosestNeighboringNodes;
    }

    private Point2D determineClosestPointInDirection(
            KeyCode direction,
            Map<Double, Point2D> distanceToClosestNeighboringNodes
    ) {

        return distanceToClosestNeighboringNodes.get(
                distanceToClosestNeighboringNodes
                        .keySet()
                        .stream()
                        .filter(key ->
                                !getMazeGraph().getFurthestReachableNode(
                                        distanceToClosestNeighboringNodes.get(key),
                                        direction
                                ).equals(distanceToClosestNeighboringNodes.get(key)))
                        .min(Comparator.comparing(Double::doubleValue))
                        .orElse(Double.MAX_VALUE) // starting point is reachable via Double.MAX_VALUE
        );
    }

    private boolean roundedPointsRepresentSameScreenLocation(Point2D startingPoint, Point2D endPoint) {

        return endPoint.getX() == Math.round(startingPoint.getX())
                && endPoint.getY() == Math.round(startingPoint.getY());
    }

    private void resetPath() {

        if (path.getElements().size() > 0)
            path.getElements().clear();

        log.trace("Reset path.");
    }

    private void movePathToStartingPointAndResetDuration(Point2D startingPoint) {

        MoveTo moveTo = new MoveTo(startingPoint.getX() + 16, startingPoint.getY() + 16);
        path.getElements().add(moveTo);

        if (!duration.equals(Duration.ZERO))
            duration = duration.subtract(duration);

        log.trace("Moved beginning path center point to {} and reset duration.", moveTo);
    }

    private void linePathTo(Point2D nextPathPoint) {

        Point2D currentPathCenterPoint = determineCurrentPathCenterPoint();
        log.trace("Current path center point at {}", currentPathCenterPoint);

        LineTo lineTo = new LineTo(nextPathPoint.getX() + 16, nextPathPoint.getY() + 16);
        path.getElements().add(lineTo);
        log.trace("Extended path on direct line to: {}", lineTo);

        Point2D nextPathCenterPoint = nextPathPoint.add(16.0D, 16.0D);
        duration = duration.add(
                calculateDurationAtConfiguredVelocity(currentPathCenterPoint, nextPathCenterPoint)
        );
        log.trace("Added new path to animation duration which covers now {} milliseconds.", duration.toMillis());
    }

    private Point2D determineCurrentPathCenterPoint() {

        PathElement currentPathElement = path.getElements().get(path.getElements().size() - 1);

        Point2D currentPathPoint;
        if (currentPathElement instanceof MoveTo)
            currentPathPoint = new Point2D(((MoveTo) currentPathElement).getX(), ((MoveTo) currentPathElement).getY());
        else
            currentPathPoint = new Point2D(((LineTo) currentPathElement).getX(), ((LineTo) currentPathElement).getY());

        return currentPathPoint;
    }

    private Set<KeyCode> determineOrthogonalKeyCodesFor(KeyCode keyCode) {

        switch (keyCode) {

            case UP:
            case DOWN:
                return Set.of(KeyCode.RIGHT, KeyCode.LEFT);
            case RIGHT:
            case LEFT:
                return Set.of(KeyCode.UP, KeyCode.DOWN);
            default:
                return new HashSet<>();
        }
    }


    public PathTransition getMoveAnimation() {

        return (PathTransition) moveAnimation;
    }

    @SuppressWarnings("unused")
    public double getMaxOffset() {

        return maxOffset.get();
    }

    @SuppressWarnings("unused")
    public void setMaxOffset(double maxOffset) {

        this.maxOffset.set(maxOffset);
    }

    @SuppressWarnings("unused")
    public DoubleProperty maxOffsetProperty() {

        return maxOffset;
    }
}
