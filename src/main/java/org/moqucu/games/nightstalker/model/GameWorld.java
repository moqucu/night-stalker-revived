package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.util.*;

@Getter
public class GameWorld implements Resettable {

    private double time = 0;

    private final Map<String, GameObject> objects = new HashMap<>();

    private final Set<TimeListener> timeListeners = new HashSet<>();

    private final Set<DisplayableObject> displayableObjects = new HashSet<>();

    private final MazeGraph mazeGraph = new MazeGraph();

    private void advanceTime(double milliseconds) {

        timeListeners.forEach(timeListener -> timeListener.elapseTime(milliseconds));
    }

    private void detectCollisions() {

        displayableObjects.stream().filter(DisplayableObject::canChangePosition).forEach(
                displayableObject -> displayableObjects
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
        if (gameObject instanceof DisplayableObject)
            displayableObjects.add((DisplayableObject) gameObject);
    }

    public void remove(GameObject gameObject) {

        objects.remove(gameObject.getObjectId());
        if (gameObject instanceof TimeListener)
            timeListeners.remove(gameObject);
        if (gameObject instanceof DisplayableObject)
            displayableObjects.remove(gameObject);
    }

    @Override
    public void reset() {

        getObjects()
                .values()
                .stream()
                .filter(gameObject -> gameObject instanceof Resettable)
                .forEach(gameObject -> ((Resettable) gameObject).reset());
    }
}
