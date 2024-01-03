package org.moqucu.games.nightstalker.model;

import java.util.*;

public class OuterRing implements MazeAlgorithmImpl {

    public static class UnacceptableDirectionException extends RuntimeException {

        UnacceptableDirectionException(String message) {

            super(message);
        }
    }

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        List<Direction> directionSequence = switch (absPosAndDirection.direction()) {
            case Up -> Arrays.asList(Direction.Right, Direction.Left, Direction.Up, Direction.Down);
            case Down -> Arrays.asList(Direction.Left, Direction.Right, Direction.Down, Direction.Up);
            case Left -> Arrays.asList(Direction.Up, Direction.Down, Direction.Left, Direction.Right);
            case Right -> Arrays.asList(Direction.Down, Direction.Up, Direction.Right, Direction.Left);
            default -> throw new UnacceptableDirectionException(
                    String.format("%s is an unacceptable direction!", absPosAndDirection.direction())
            );
        };
        int i = 0;
        while (
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.absolutePosition(),
                        directionSequence.get(i)
                ).equals(absPosAndDirection.absolutePosition())
        )
            i++;

        return new AbsPosAndDirection(
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.absolutePosition(),
                        directionSequence.get(i)
                ),
                directionSequence.get(i)
        );
    }
}
