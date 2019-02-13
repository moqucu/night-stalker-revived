package org.moqucu.games.nightstalker.gameobject.immovable;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.gameobject.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wall extends Sprite {

    public Wall(Image image, Position initialPosition) {

        super(initialPosition);
        setImage(image);
    }
}
