package org.moqucu.games.nightstalker.utility;

import javafx.scene.Group;

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
            if (
                    createdSprite.getClass().equals(Group.class)
                            && (
                                    ((Group) createdSprite).getId() == null
                                            || !((Group) createdSprite).getId().trim().equals("GreyRobotAnimationGroup"))
            )
                System.out.println("NOP");
                // do nothing
            else
                createdSprites.put(createdSprite.getClass(), createdSprite);
    }
}
