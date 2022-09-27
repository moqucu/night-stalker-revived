package org.moqucu.games.nightstalker.model;

import java.util.Arrays;
import java.util.List;

public class Random implements MazeAlgorithmImpl {

    public static class UnacceptableDirectionException extends RuntimeException {

        UnacceptableDirectionException(String message) {

            super(message);
        }
    }

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        List<Direction> directionSequence;
        switch (absPosAndDirection.getDirection()) {
            case Up:
                directionSequence = Arrays.asList(Direction.Right, Direction.Left, Direction.Up, Direction.Down);
                break;
            case Down:
                directionSequence = Arrays.asList(Direction.Left, Direction.Right, Direction.Down, Direction.Up);
                break;
            case Left:
                directionSequence = Arrays.asList(Direction.Up, Direction.Down, Direction.Left, Direction.Right);
                break;
            case Right:
                directionSequence = Arrays.asList(Direction.Down, Direction.Up, Direction.Right, Direction.Left);
                break;
            default:
                throw new UnacceptableDirectionException(
                        String.format("%s is an unacceptable direction!", absPosAndDirection.getDirection())
                );
        }
        int i = 0;
        while (
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.getAbsolutePosition(),
                        directionSequence.get(i)
                ).equals(absPosAndDirection.getAbsolutePosition())
        )
            i++;

        return new AbsPosAndDirection(
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.getAbsolutePosition(),
                        directionSequence.get(i)
                ),
                directionSequence.get(i)
        );
    }
}
