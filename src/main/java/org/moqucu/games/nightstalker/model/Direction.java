package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enum represents 5 logical directions when comparing two points in 2D: up, down, left, right, on-top.
 */
public enum Direction {

    Up, Down, Left, Right, OnTop, Undefined;

    /**
     * Given two points, determine the direction that targetPoint is relative to sourcePoint.
     * This assumes that a point can only have on relative direction, meaning that either the
     * x-coordinate or y-coordinate is identical. When both coordinates are identical, on-top
     * is being considered the right correct. When both coordinates are different, the target
     * point will always be considered right of the source point.
     * @param sourcePoint Starting point for determining the direction.
     * @param targetPoint Target point for determining the direction.
     * @return Either Up, Down, Right, Left, or OnTop, depending on the two points relative coordinates.
     */
    @Deprecated
    public static Direction calculateDirection(Point2D sourcePoint, Point2D targetPoint) {

        return calculateDirection(
                new AbsolutePosition(sourcePoint.getX(), sourcePoint.getY()),
                new AbsolutePosition(targetPoint.getX(), targetPoint.getY())
        );
    }

    public static Direction calculateDirection(AbsolutePosition sourcePos, AbsolutePosition targetPos) {

        Set<Direction> availableDirections = new HashSet<>(Set.of(Up, Down, Left, Right, OnTop));

        double deltaX = targetPos.getX() - sourcePos.getX();
        double deltaY = targetPos.getY() - sourcePos.getY();

        if (deltaX == 0. && deltaY == 0.)
            List.of(Left,Right, Up, Down).forEach(availableDirections::remove);
        else
            availableDirections.remove(OnTop);

        if (deltaX < 0.)
            availableDirections.remove(Right);
        else if (deltaX > 0.)
            availableDirections.remove(Left);
        else {
            availableDirections.remove(Right);
            availableDirections.remove(Left);
        }

        if (deltaY < 0.)
            availableDirections.remove(Down);
        else if (deltaY > 0.)
            availableDirections.remove(Up);
        else {
            availableDirections.remove(Down);
            availableDirections.remove(Up);
        }

        if (availableDirections.size() != 1)
            return Direction.Right;
        else
            return availableDirections.toArray(new Direction[]{})[0];
    }
}
