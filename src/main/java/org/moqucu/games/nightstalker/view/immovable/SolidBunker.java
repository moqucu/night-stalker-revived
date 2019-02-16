package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class SolidBunker extends Sprite {

    public SolidBunker() {

        super(Point2D.ZERO);
    }

    public SolidBunker(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }
}
