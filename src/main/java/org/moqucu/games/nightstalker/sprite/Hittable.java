package org.moqucu.games.nightstalker.sprite;

import java.util.Set;

/**
 * When classes want to indicate that they are 'hittable' by other classes, they shall implement this interface.
 */
public interface Hittable {

    /**
     * This method needs to be implemented for detecting collisions with other objects. Right now, the actual collision
     * detection has to happen de-centrally as part of every class that implements this method.
     *
     * @param nearbySprites The set of sprites that is handed into this method and could cause a collision.
     */
    void detectCollision(Set<AnimatedSprite> nearbySprites);
}
