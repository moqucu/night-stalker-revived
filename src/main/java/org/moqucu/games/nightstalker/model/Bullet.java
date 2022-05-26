package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;
import org.moqucu.games.nightstalker.sprite.Collidable;

/**
 * Represents a bullet. Marker interface, no behavior other than what is being inherited from Collidable.
 */
public interface Bullet extends Collidable {

    void shot(Direction direction, Point2D startPoint);
}
