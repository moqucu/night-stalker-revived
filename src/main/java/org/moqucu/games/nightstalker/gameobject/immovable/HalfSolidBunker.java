package org.moqucu.games.nightstalker.gameobject.immovable;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.gameobject.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class HalfSolidBunker extends Sprite {

    public HalfSolidBunker(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }

    public Bounds getBoundary() {

        return new BoundingBox(currentCoordinates.getX(), currentCoordinates.getY() + HEIGHT / 2.0, WIDTH, HEIGHT);
    }
}
