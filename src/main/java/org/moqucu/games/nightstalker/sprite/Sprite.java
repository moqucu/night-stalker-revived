package org.moqucu.games.nightstalker.sprite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.Maze;

@Data
@Log4j2
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends ImageView {

    public static final double WIDTH = 32.0;

    public static final double HEIGHT = 32.0;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private IntegerProperty stillImageIndex = new SimpleIntegerProperty(0);

    public Sprite() {

        super();
        setStillImageIndex(stillImageIndex.get());
    }

    /**
     * Returns a rectangle representing the sprite image at the given index position. The index has to be
     * between 0 and 239 which represents all possible 240 tiles in the 20 x 12 matrix.
     *
     * @param index Index of the tile to be retrieved as a rectangular viewport.
     * @return Rectangle that represents the boundaries of the accessed tile.
     * @throws RuntimeException if index is below 0 or above 239.
     */
    @SuppressWarnings("WeakerAccess")
    protected Rectangle2D getViewport(int index) {

        if (index < 0 || index > 239)
            throw new RuntimeException("Frame index " + index + "is out of bounds, has to be between 0 and 239!");

        int horizontalIndex = index % 20;
        int verticalIndex = index / 20;

        return new Rectangle2D(horizontalIndex * WIDTH, verticalIndex * HEIGHT, WIDTH, HEIGHT);
    }

    public int getStillImageIndex() {

        return stillImageIndex.get();
    }

    @SuppressWarnings("WeakerAccess")
    public void setStillImageIndex(int index) {

        stillImageIndex.set(index);
        setViewport(getViewport(stillImageIndex.get()));
    }

    public IntegerProperty stillImageIndexProperty() {

        return stillImageIndex;
    }

    protected Maze getMaze() {

        boolean foundParentOrNothing = false;
        Parent parent = this.getParent();
        while (!foundParentOrNothing) {

            if (parent instanceof Maze || parent == null)
                foundParentOrNothing = true;
            else
                parent = parent.getParent();
        }

        return (Maze)parent;
    }
}
