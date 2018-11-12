package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bunker extends Sprite implements Renderable {

    public Bunker(Image image, Position initialPosition) {

        super(initialPosition);
        setInitialImage(image);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public boolean intersects(Sprite sprite) {

        if (sprite instanceof NightStalker)
            return false;
        else
            return sprite.getBoundary().intersects(this.getBoundary());
    }
}
