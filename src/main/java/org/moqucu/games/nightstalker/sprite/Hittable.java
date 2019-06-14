package org.moqucu.games.nightstalker.sprite;

/**
 * When classes want to indicate that they are 'hittable' by other classes, they shall implement this interface.
 */
public interface Hittable {

    /**
     * This method needs to be implemented for detecting collisions with other objects. Only objects that are in a
     * state that allows for collisions and that intersect with an instance of Hittable will be passed as a parameter.
     *
     * @param collidableObject The sprite that collides with the object.
     */
    void hitBy(Collidable collidableObject);

    /**
     * This method indicate whether an object that principally allows to be collided with, is currently
     * in a state .
     *
     * @return true when object's internal state allows for being hit, otherwise false.
     */
    boolean isHittable();
}
