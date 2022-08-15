package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import org.moqucu.games.nightstalker.event.TimeListener;

import java.util.*;

public class GameWorld {

    @Getter
    private long time = 0;

    @Getter
    private final Map<UUID, GameObject> objects = new HashMap<>();

    @Getter
    private final Set<TimeListener> timeListeners = new HashSet<>();

    public void pulse(long milliseconds) {

        time += milliseconds;
        timeListeners.forEach(timeListener -> timeListener.elapseTime(milliseconds));
    }

    public void add(GameObject gameObject) {

        objects.put(gameObject.getId(), gameObject);
        if (gameObject instanceof TimeListener)
            timeListeners.add((TimeListener)gameObject);
    }
}