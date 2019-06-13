package org.moqucu.games.nightstalker.sprite;

/**
 * When classes want to indicate that they are 'hittable' by other classes, they shall implement this interface.
 */
public interface Hittable {

    /**
     * This method needs to be implemented for detecting collisions with other objects. Each object has to decide
     * internally which objects it can collide with and how to react in case of a collision.
     *
     * @param collidableSprite The sprite that collides with the object.
     */
    void detectCollision(Collidable collidableSprite);

    /**
     * This method indicate whether an object that principally allows to be collided with, is currently
     * in a state .
     *
     * @return true when object's internal state allows for being hit, otherwise false.
     */
    boolean isHittable();

}
