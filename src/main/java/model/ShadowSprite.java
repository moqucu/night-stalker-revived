package model;

import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShadowSprite extends Sprite {

    private GameObject shadowCaster;

    ShadowSprite(GameObject shadowCaster, int x, int y) {

        super(Sprite.Coordinates.builder().x(x).y(y).build());
        this.shadowCaster = shadowCaster;
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {
    }
}
