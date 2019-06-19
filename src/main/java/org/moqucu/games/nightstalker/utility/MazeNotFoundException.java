package org.moqucu.games.nightstalker.utility;

/**
 * Exception is thrown whenever expected Maze object couldn't be found in sprite's parent hierarchy.
 */
public class MazeNotFoundException extends RuntimeException {

    public MazeNotFoundException() {

        super("No Maze object found as part of sprite's parent hierarchy!");
    }
}
