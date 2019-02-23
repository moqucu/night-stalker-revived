package org.moqucu.games.nightstalker.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.*;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends ImageView {

    public static final double WIDTH = 32.0;

    public static final double HEIGHT = 32.0;

    private Image initialImage;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private IntegerProperty initialKeyFrameIndex = new SimpleIntegerProperty(0);

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


    protected Point2D currentCoordinates = Point2D.ZERO;

    public Sprite(Position initialMazeGridPosition) {

        setViewport(getViewPort(initialKeyFrameIndex.get()));
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

    public Sprite() {

        super();
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

    private Rectangle2D getViewPort(int index) {

        return new Rectangle2D(index * WIDTH, 0, WIDTH, HEIGHT);
    }

    public int getInitialKeyFrameIndex() {

        return initialKeyFrameIndex.get();
    }

    public void setInitialKeyFrameIndex(int index) {

        initialKeyFrameIndex.set(index);
        setViewport(getViewPort(initialKeyFrameIndex.get()));
    }

    public IntegerProperty initialKeyFrameIndexProperty() {

        return initialKeyFrameIndex;
    }
}
