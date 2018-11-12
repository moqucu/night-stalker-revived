package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SolidBunker extends Sprite implements Renderable {

    public SolidBunker(Image image, Position initialPosition) {

        super(initialPosition);
        setInitialImage(image);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }
}
