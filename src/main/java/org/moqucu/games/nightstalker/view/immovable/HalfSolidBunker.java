package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;

@Data
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class HalfSolidBunker extends Sprite {

    public HalfSolidBunker() {

        super(Point2D.ZERO);
    }

    public HalfSolidBunker(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }
}
