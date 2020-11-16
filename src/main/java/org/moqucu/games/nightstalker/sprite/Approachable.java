package org.moqucu.games.nightstalker.sprite;

import javafx.scene.shape.Line;

import java.util.Set;

/**
 * When sprites want to indicate that they can be 'approached' by other sprites, they shall implement this interface.
 */
public interface Approachable {

    /**
     * This method indicate whether an sprite is in direct line of sight and thus approaching.
     */
    void approachedBy(Set<Sprite> sprite);

    Line getLineOfSight();
}
