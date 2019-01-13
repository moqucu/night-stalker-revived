package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends GameObject implements Renderable {

    @Data
    @Builder
    @SuppressWarnings("WeakerAccess")
    public static class Coordinates {

        private long x;
        private long y;
    }

    /**
     * Velocity is measured in pixel / seconds
     */
    private int velocity;

    protected double frameDuration;

    private Image initialImage;

    private Coordinates currentCoordinates = Coordinates.builder().build();

    Sprite(Position initialPosition) {

        super(initialPosition);
        currentCoordinates.setX(getInitialPosition().getHorizontal() * WIDTH);
        currentCoordinates.setY(getInitialPosition().getVertical() * HEIGHT);
    }

    Sprite(Coordinates initialCoordinates) {

        super(null);
        currentCoordinates = initialCoordinates;
    }

    public Rectangle2D getBoundary() {

        return new Rectangle2D(currentCoordinates.getX(), currentCoordinates.getY(), WIDTH, HEIGHT);
    }

    protected boolean intersects(Sprite sprite) {

        return sprite.getBoundary().intersects(this.getBoundary());
    }

    long determinePixelMoveRate(double deltaTime) {

        return Math.round(getVelocity() * deltaTime);
    }
}
