package org.moqucu.games.nightstalker.sprite;

import java.util.Set;

public interface Hittable {

    void detectCollision(Set<AnimatedSprite> nearbySprites);
}
