package org.moqucu.games.nightstalker.view;

import javafx.geometry.Point2D;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShadowSprite extends Sprite {

    private Sprite shadowCaster;

    public ShadowSprite(Sprite shadowCaster, double x, double y) {

        super();
        this.shadowCaster = shadowCaster;
    }
}
