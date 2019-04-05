package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ManuallyMovedSprite extends MovableSprite {

    private static final double MAX_OFFSET = 8.0;

    private Animation translateTransition;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final DoubleProperty maxOffset = new SimpleDoubleProperty(MAX_OFFSET);

    @SneakyThrows
    ManuallyMovedSprite() {

        super();
    }

    PathTransition calculatePathTransition(Point2D point, KeyCode direction) {

        log.info("Starting point: {}", point);

        Path path = new Path();
        Duration duration = new Duration(0);

        MoveTo moveTo = new MoveTo(
                point.getX() + 16,
                point.getY() + 16
        );

        path.getElements().add(moveTo);
        log.info("Computed moveTo shape: {}", moveTo);

        Point2D furthestReachableNode = getMazeGraph().getFurthestReachableNode(point, direction);
        log.info("Initial value for furthest reachable point: {}", furthestReachableNode);

        if (furthestReachableNode.getX() == Math.round(point.getX()) && furthestReachableNode.getY() == Math.round(point.getY())) {

            Map<Double, Point2D> closestOrthogonalNodes = new HashMap<>(2);
            closestOrthogonalNodes.put(Double.MAX_VALUE, point);

            log.info("No direct movement possible!");
            determineOrthogonalKeyCodes(direction).forEach(keyCode -> {

                Point2D closedNodeInDirection = getMazeGraph().getClosestReachableNode(point, keyCode, maxOffset.get());
                closestOrthogonalNodes.put(point.distance(closedNodeInDirection), closedNodeInDirection);
            });

            Point2D closestPoint = closestOrthogonalNodes.get(
                    closestOrthogonalNodes
                            .keySet()
                            .stream()
                            .filter(key ->
                                !getMazeGraph().getFurthestReachableNode(closestOrthogonalNodes.get(key), direction).equals(closestOrthogonalNodes.get(key)))
                            .min(Comparator.comparing(Double::doubleValue))
                            .orElse(Double.MAX_VALUE)
            );

            if (!closestPoint.equals(point)) {

                LineTo lineTo = new LineTo(
                        closestPoint.getX() + 16,
                        closestPoint.getY() + 16
                );
                path.getElements().add(lineTo);
                log.info("Computed lineTo shape: {}", lineTo);

                duration = duration.add(calculateDurationAtConfiguredVelocity(point, closestPoint));
                log.info("Calculated duration: {}", duration);

                Point2D otherFurthestReachableNode = getMazeGraph().getFurthestReachableNode(closestPoint, direction);
                LineTo lineTo2 = new LineTo(
                        otherFurthestReachableNode.getX() + 16,
                        otherFurthestReachableNode.getY() + 16
                );
                path.getElements().add(lineTo2);
                log.info("Computed lineTo shape: {}", lineTo2);

                duration = duration.add(calculateDurationAtConfiguredVelocity(closestPoint, otherFurthestReachableNode));
            }


            log.info("Closest alternative point: {}", closestPoint);
        } else {

            LineTo lineTo = new LineTo(
                    furthestReachableNode.getX() + 16,
                    furthestReachableNode.getY() + 16
            );
            path.getElements().add(lineTo);
            log.info("Computed lineTo shape: {}", lineTo);

            duration = duration.add(calculateDurationAtConfiguredVelocity(point, furthestReachableNode));
            log.info("Calculated duration: {}", duration);
        }

        PathTransition pathTransition = new PathTransition(duration, path, this);
        pathTransition.setCycleCount(1);
        pathTransition.setInterpolator(Interpolator.LINEAR);

        return pathTransition;
    }

    private Set<KeyCode> determineOrthogonalKeyCodes(KeyCode keyCode) {

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
