package org.moqucu.games.nightstalker.objects.immovable;

import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.objects.GameObject;
import org.moqucu.games.nightstalker.objects.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShadowSprite extends Sprite {

    private GameObject shadowCaster;

    public ShadowSprite(GameObject shadowCaster, long x, long y) {

        super(Sprite.Coordinates.builder().x(x).y(y).build());
        this.shadowCaster = shadowCaster;
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {
    }
}
