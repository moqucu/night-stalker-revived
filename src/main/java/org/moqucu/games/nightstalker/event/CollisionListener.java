package org.moqucu.games.nightstalker.event;

import org.moqucu.games.nightstalker.model.GameObject;

public interface CollisionListener {

    void collideWith(GameObject gameObject);
}
