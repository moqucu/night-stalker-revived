package org.moqucu.games.nightstalker.model;

public class FollowDirection implements MazeAlgorithmImpl {

    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        return new AbsPosAndDirection(
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.getAbsolutePosition(),
                        absPosAndDirection.getDirection()
                ),
                absPosAndDirection.getDirection()
        );
    }
}
