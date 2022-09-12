package org.moqucu.games.nightstalker.event;

import org.moqucu.games.nightstalker.model.GameObjectImpl;

public interface CollisionListener {

    void collideWith(GameObjectImpl gameObject);
}
