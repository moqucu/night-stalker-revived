package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import lombok.Setter;

public abstract class CollidableImpl implements Collidable {

    @Getter
    @Setter
    private BoundingBox boundingBox;

    public CollidableImpl() {

        boundingBox = new BoundingBox();
    }

    public CollidableImpl(BoundingBox boundingBox) {

        this.boundingBox = boundingBox;
    }

    @Override
    public boolean isCollidingWith(Collidable anotherCollidable) {

        return getAbsoluteBounds().isOverlapping(anotherCollidable.getAbsoluteBounds());
    }

    @Override
    public void collisionOccuredWith(Collidable anotherCollidable) {
    }
}
