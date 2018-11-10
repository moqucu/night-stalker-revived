package model;

import javafx.scene.canvas.GraphicsContext;

public class TransparentSprite extends Sprite {

    TransparentSprite(int x, int y) {

        super(Sprite.Coordinates.builder().x(x).y(y).build());
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {
    }
}
