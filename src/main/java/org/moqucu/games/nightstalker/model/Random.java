package org.moqucu.games.nightstalker.model;

import java.util.HashSet;
import java.util.Set;

public class Random implements MazeAlgorithmImpl {

    public final java.util.Random random = new java.util.Random();

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        if (!absMazeGraph.isOnNode(absPosAndDirection.getAbsolutePosition()))

            return new AbsPosAndDirection(
                    absMazeGraph.getClosestReachablePosition(
                            absPosAndDirection.getAbsolutePosition(),
                            absPosAndDirection.getDirection()
                    ),
                    absPosAndDirection.getDirection()
            );

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

        if (reachablePositions.size() > 1)
            reachablePositions.removeIf(
                    absPosAndDirectionAlternative -> absPosAndDirectionAlternative
                            .getDirection()
                            .equals(Direction.opposite(absPosAndDirection.getDirection()))
            );

        final int index = random.ints(0, reachablePositions.size()).findFirst().orElseThrow();
        return (AbsPosAndDirection) reachablePositions.toArray()[index];
    }
}
