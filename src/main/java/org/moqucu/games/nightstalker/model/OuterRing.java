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

        //todo: this algorithm needs to be hand-crafted
        List<Direction> directionSequence;
        switch (absPosAndDirection.getDirection()) {
            case Up:
                directionSequence = Arrays.asList(Direction.Up, Direction.Left, Direction.Down, Direction.Right);
                break;
            case Down:
                directionSequence = Arrays.asList(Direction.Down, Direction.Right, Direction.Up, Direction.Left);
                break;
            case Left:
                directionSequence = Arrays.asList(Direction.Left, Direction.Down, Direction.Right, Direction.Up);
                break;
            case Right:
                directionSequence = Arrays.asList(Direction.Right, Direction.Up, Direction.Left, Direction.Down);
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
