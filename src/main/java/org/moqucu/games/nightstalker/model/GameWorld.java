package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.util.*;

public class GameWorld {

    @Getter
    private double time = 0;

    @Getter
    private final Map<String, GameObject> objects = new HashMap<>();

    @Getter
    private final Set<TimeListener> timeListeners = new HashSet<>();

    @Getter
    private final MazeGraph mazeGraph = new MazeGraph();

    private void advanceTime(double milliseconds) {

        timeListeners.forEach(timeListener -> timeListener.elapseTime(milliseconds));
    }

    private Set<DisplayableObject> getDisplayObjects() {

        final Set<DisplayableObject> displayableObjects = new HashSet<>();

        objects
                .values()
                .stream()
                .filter(gameObject -> gameObject instanceof DisplayableObject)
                .forEach(gameObject -> displayableObjects.add((DisplayableObject) gameObject));

        return displayableObjects;
    }

    private void detectCollisions() {

        getDisplayObjects().stream().filter(DisplayableObject::canChangePosition).forEach(
                displayableObject -> getDisplayObjects()
                        .stream()
                        .filter(otherGameObject -> !otherGameObject.equals(displayableObject))
                        .forEach(
                                otherGameObjectButNotItself -> {
                                    if (displayableObject.isCollidingWith(otherGameObjectButNotItself)) {
                                        displayableObject.collisionOccurredWith(otherGameObjectButNotItself);
                                        if (!otherGameObjectButNotItself.canChangePosition())
                                            otherGameObjectButNotItself.collisionOccurredWith(displayableObject);
                                    }
                                }
                        )
        );
    }

    public void pulse(double milliseconds) {

        time += milliseconds;
        advanceTime(milliseconds);
        detectCollisions();
    }

    public void add(GameObject gameObject) {

        objects.put(gameObject.getObjectId(), gameObject);
        if (gameObject instanceof TimeListener)
            timeListeners.add((TimeListener) gameObject);
    }
}
