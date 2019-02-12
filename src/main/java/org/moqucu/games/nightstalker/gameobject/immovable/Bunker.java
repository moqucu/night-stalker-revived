package org.moqucu.games.nightstalker.gameobject.immovable;

import javafx.scene.image.Image;
import org.moqucu.games.nightstalker.gameobject.movable.NightStalker;
import org.moqucu.games.nightstalker.gameobject.Sprite;

public class Bunker extends Sprite {

    public Bunker(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }

    @Override
    public boolean intersects(Sprite sprite) {

        if (sprite instanceof NightStalker)
            return false;
        else
            return sprite.getBoundary().intersects(this.getBoundary());
    }
}
