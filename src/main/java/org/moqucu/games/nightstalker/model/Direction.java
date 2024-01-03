package org.moqucu.games.nightstalker.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enum represents 5 logical directions when comparing two points in 2D: up, down, left, right, on-top.
 */
public enum Direction {

    Up, Down, Left, Right, OnTop, Undefined;

    public static class NoOppositeDirectionAvailable extends RuntimeException {

        NoOppositeDirectionAvailable(String message) {

            super(message);
        }
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

    public static Direction opposite(Direction direction) {

        return switch (direction) {
            case Right -> Direction.Left;
            case Left -> Direction.Right;
            case Down -> Direction.Up;
            case Up -> Direction.Down;
            default -> throw new NoOppositeDirectionAvailable(
                    String.format("No opposite direction available to %s!", direction)
            );
        };
    }
}
