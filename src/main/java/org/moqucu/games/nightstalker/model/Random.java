package org.moqucu.games.nightstalker.model;

import java.util.HashSet;
import java.util.Set;

public class Random implements MazeAlgorithmImpl {

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        final Set<AbsPosAndDirection> reachablePositions = new HashSet<>();
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.getAbsolutePosition(),
                                Direction.Left
                        ),
                        Direction.Left
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.getAbsolutePosition(),
                                Direction.Up
                        ),
                        Direction.Up
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.getAbsolutePosition(),
                                Direction.Right
                        ),
                        Direction.Right
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.getAbsolutePosition(),
                                Direction.Down
                        ),
                        Direction.Down
                )
        );
        reachablePositions.removeIf(
                absPosAndDirectionAlternative -> absPosAndDirectionAlternative
                        .getAbsolutePosition()
                        .equals(absPosAndDirection.getAbsolutePosition())
        );

        return reachablePositions.stream().unordered().findFirst().orElseThrow();
    }
}
