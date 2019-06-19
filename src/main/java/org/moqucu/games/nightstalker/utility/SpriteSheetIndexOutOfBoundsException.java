package org.moqucu.games.nightstalker.utility;

/**
 * Exception is thrown whenever expected the index for accessing a sprite sheet's cell is < 0 or > 239.
 */
public class SpriteSheetIndexOutOfBoundsException extends RuntimeException {

    public SpriteSheetIndexOutOfBoundsException() {

        super("Index for accessing the sprite sheet has to be between 0 and 239!");
    }
}
