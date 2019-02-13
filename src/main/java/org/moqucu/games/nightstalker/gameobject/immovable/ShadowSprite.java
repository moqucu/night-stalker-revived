package org.moqucu.games.nightstalker.gameobject.immovable;

import javafx.geometry.Point2D;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.gameobject.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShadowSprite extends Sprite {

    private Sprite shadowCaster;

    public ShadowSprite(Sprite shadowCaster, double x, double y) {

        super(new Point2D(x, y));
        this.shadowCaster = shadowCaster;
    }
}
