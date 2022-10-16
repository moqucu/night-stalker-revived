package org.moqucu.games.nightstalker.model;

public interface Collidable {

    BoundingBox getBoundingBox();

    void setBoundingBox(BoundingBox boundingBox);

    BoundingBox getAbsoluteBounds();

    boolean isCollidingWith(Collidable anotherCollidable);
}
