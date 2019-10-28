package org.moqucu.games.nightstalker.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SpriteCreationListener {

    List<Class<?>> spriteClassesOfInterest = new ArrayList<>();

    Map<Class<?>, Object> createdSprites = new HashMap<>();

    default void registerSpriteClass(Class<?> spriteClassOfInterest) {

        if (!spriteClassesOfInterest.contains(spriteClassOfInterest))
            spriteClassesOfInterest.add(spriteClassOfInterest);
    }

    default void addCreatedSprite(Object createdSprite) {

        if (spriteClassesOfInterest.contains(createdSprite.getClass()))
            createdSprites.put(createdSprite.getClass(), createdSprite);
    }
}
