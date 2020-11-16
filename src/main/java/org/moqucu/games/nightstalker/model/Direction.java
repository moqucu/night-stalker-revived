package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public enum Direction {

    Up, Down, Left, Right, Undefined;

    public static Direction calculateDirection(Point2D sourcePoint, Point2D targetPoint) {

        Set<Direction> availableDirections = new HashSet<>(Set.of(Up, Down, Left, Right));

        double deltaX = targetPoint.getX() - sourcePoint.getX();
        double deltaY = targetPoint.getY() - sourcePoint.getY();

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

        if (availableDirections.size() != 1.)
            return Direction.Right;
        else
            return availableDirections.toArray(new Direction[]{})[0];
    }
}
