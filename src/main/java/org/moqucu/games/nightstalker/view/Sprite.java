package org.moqucu.games.nightstalker.view;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends ImageView {

    public static final double WIDTH = 32.0;

    public static final double HEIGHT = 32.0;

    @Data
    @Builder
    protected static class Position {

        private int horizontal;
        private int vertical;
    }

    /**
     * Velocity is measured in pixel / seconds
     */
    private int velocity;

    private Position initialMazeGridPosition;

    protected double frameDuration;

    private Image initialImage;

    protected Point2D currentCoordinates = Point2D.ZERO;

    public Sprite(Position initialMazeGridPosition) {

        this.initialMazeGridPosition = initialMazeGridPosition;
        currentCoordinates.add(
                initialMazeGridPosition.getHorizontal() * WIDTH,
                initialMazeGridPosition.getVertical() * HEIGHT
        );
    }

    public Sprite(Point2D initialCoordinates) {

        this(Position.builder().build());
        currentCoordinates = initialCoordinates;
        relocate(currentCoordinates.getX(), currentCoordinates.getY());
    }

    public Bounds getBoundary() {

        return new BoundingBox(currentCoordinates.getX(), currentCoordinates.getY(), WIDTH, HEIGHT);
    }

    public boolean intersects(Sprite sprite) {

        return sprite.getBoundary().intersects(this.getBoundary());
    }

    protected long determinePixelMoveRate(double deltaTime) {

        return Math.round(velocity * deltaTime);
    }
}
