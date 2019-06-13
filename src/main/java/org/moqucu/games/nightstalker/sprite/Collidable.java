package org.moqucu.games.nightstalker.sprite;

import java.util.Set;

/**
 * When classes want to indicate that they can 'collide' with other classes, they shall implement this interface.
 */
public interface Collidable {

    /**
     * This method indicate whether an object that principally allows to be 'detected' for collisions, is currently
     * 'collidable' or not.
     *
     * @return true when object's internal state allows for collision, otherwise false.
     */
    boolean isCollidable();
}
