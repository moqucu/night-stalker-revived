package org.moqucu.games.nightstalker.model;

import java.util.HashSet;
import java.util.Set;

public class Random implements MazeAlgorithmImpl {

    public final java.util.Random random = new java.util.Random();

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        if (!absMazeGraph.isOnNode(absPosAndDirection.absolutePosition()))

            return new AbsPosAndDirection(
                    absMazeGraph.getClosestReachablePosition(
                            absPosAndDirection.absolutePosition(),
                            absPosAndDirection.direction()
                    ),
                    absPosAndDirection.direction()
            );

        final Set<AbsPosAndDirection> reachablePositions = new HashSet<>();
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.absolutePosition(),
                                Direction.Left
                        ),
                        Direction.Left
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.absolutePosition(),
                                Direction.Up
                        ),
                        Direction.Up
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.absolutePosition(),
                                Direction.Right
                        ),
                        Direction.Right
                )
        );
        reachablePositions.add(
                new AbsPosAndDirection(
                        absMazeGraph.getClosestReachablePosition(
                                absPosAndDirection.absolutePosition(),
                                Direction.Down
                        ),
                        Direction.Down
                )
        );
        reachablePositions.removeIf(
                absPosAndDirectionAlternative -> absPosAndDirectionAlternative
                        .absolutePosition()
                        .equals(absPosAndDirection.absolutePosition())
        );

        if (reachablePositions.size() > 1)
            reachablePositions.removeIf(
                    absPosAndDirectionAlternative -> absPosAndDirectionAlternative
                            .direction()
                            .equals(Direction.opposite(absPosAndDirection.direction()))
            );

        final int index = random.ints(0, reachablePositions.size()).findFirst().orElseThrow();
        return (AbsPosAndDirection) reachablePositions.toArray()[index];
    }
}
