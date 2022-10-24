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

    private void detectCollisions() {

        objects.values().stream().filter(GameObject::canChangePosition).forEach(
                gameObject -> objects
                        .values()
                        .stream()
                        .filter(otherGameObject -> !otherGameObject.equals(gameObject))
                        .forEach(
                                otherGameObjectButNotItself -> {
                                    if (gameObject.isCollidingWith(otherGameObjectButNotItself)) {
                                        gameObject.collisionOccurredWith(otherGameObjectButNotItself);
                                        if (!otherGameObjectButNotItself.canChangePosition())
                                            otherGameObjectButNotItself.collisionOccurredWith(gameObject);
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
