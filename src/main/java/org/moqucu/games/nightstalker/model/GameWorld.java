package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import org.moqucu.games.nightstalker.event.TimeListener;

import java.util.*;

public class GameWorld {

    @Getter
    private long time = 0;

    @Getter
    private final Map<String, GameObject> objects = new HashMap<>();

    @Getter
    private final Set<TimeListener> timeListeners = new HashSet<>();

    @Getter
    private final MazeGraphV2 mazeGraph = new MazeGraphV2();

    public void pulse(long milliseconds) {

        time += milliseconds;
        timeListeners.forEach(timeListener -> timeListener.elapseTime(milliseconds));
    }

    public void add(GameObject gameObject) {

        objects.put(gameObject.getObjectId(), gameObject);
        if (gameObject instanceof TimeListener)
            timeListeners.add((TimeListener)gameObject);
    }
}
