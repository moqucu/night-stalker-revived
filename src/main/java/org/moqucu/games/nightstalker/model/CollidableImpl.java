package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import lombok.Setter;

public abstract class CollidableImpl implements Collidable {

    @Getter
    @Setter
    private BoundingBox boundingBox;

    @Override
    public boolean isCollidingWith(Collidable anotherCollidable) {

        return getAbsoluteBounds().isOverlapping(anotherCollidable.getAbsoluteBounds());
    }
}
