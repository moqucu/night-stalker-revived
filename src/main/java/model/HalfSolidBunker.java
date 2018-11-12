package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HalfSolidBunker extends Sprite implements Renderable {

    public HalfSolidBunker(Image image, Position initialPosition) {

        super(initialPosition);
        setInitialImage(image);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    protected Rectangle2D getBoundary() {

        return new Rectangle2D(
                getCurrentCoordinates().getX(),
                getCurrentCoordinates().getY()+HEIGHT/2.0,
                WIDTH,
                HEIGHT/2.0
        );
    }
}
