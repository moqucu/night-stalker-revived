package org.moqucu.games.nightstalker.model;

public interface MazeAlgorithmImpl {

    AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection);
}
