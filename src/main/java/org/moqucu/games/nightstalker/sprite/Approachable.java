package org.moqucu.games.nightstalker.sprite;

import org.moqucu.games.nightstalker.model.Direction;

/**
 * When sprites want to indicate that they can be 'approached' by other sprites, they shall implement this interface.
 */
public interface Approachable {

    /**
     * This method indicate whether an sprite is in direct line of sight and thus approaching.
     */
    void approachedBy(Sprite sprite);

    /**
     * Returns the direction of which the approachable sprite is looking.
     *
     * @return direction of which the approachable sprite is looking.
     */
    Direction getSightDirection();
}
