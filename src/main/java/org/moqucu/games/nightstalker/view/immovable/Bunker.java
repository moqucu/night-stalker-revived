package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.movable.NightStalker;

public class Bunker extends Sprite {

    public Bunker() {

        super(Point2D.ZERO);
    }

    public Bunker(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }
}
