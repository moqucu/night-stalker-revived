package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends GameObject implements Renderable {

    @Data
    public static class Coordinates {

        private int x;
        private int y;
    }

    private Image initialImage;

    private Coordinates currentCoordinates;

    Sprite(Position initialPosition) {

        super(initialPosition);
        currentCoordinates.setX(getInitialPosition().getHorizontal() * WIDTH);
        currentCoordinates.setY(getInitialPosition().getVertical() * HEIGHT);
    }

    public Rectangle2D getBoundary() {

        return new Rectangle2D(currentCoordinates.getX(), currentCoordinates.getY(), WIDTH, HEIGHT);
    }

    public boolean intersects(Sprite sprite) {

        return sprite.getBoundary().intersects(this.getBoundary());
    }
}
