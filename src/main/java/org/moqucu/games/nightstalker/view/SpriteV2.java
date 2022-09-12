package org.moqucu.games.nightstalker.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameObjectImpl;

/**
 * Represents a basic sprite class that specializes ImageView.
 */
@Log4j2
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class SpriteV2 extends ImageView implements GameObject {

    /**
     * Exception is thrown whenever expected the index for accessing a sprite sheet's cell is < 0 or > 239.
     */
    static class SpriteSheetIndexOutOfBoundsException extends RuntimeException {

        SpriteSheetIndexOutOfBoundsException() {

            super("Index for accessing the sprite sheet has to be between 0 and 239!");
        }
    }

    @Getter
    @Delegate(types=GameObject.class)
    private GameObjectImpl model;

    /**
     * Represents the cell index of the image tile that shall be used for rendering purposes. Default value is 0.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private IntegerProperty stillImageIndex = new SimpleIntegerProperty(0);

    /**
     * Constructor that sets stillImageIndex based on standard initialization value (= 0).
     */
    public SpriteV2() {

        super();
        setStillImageIndex(stillImageIndex.get());
    }

//    /**
//     * Returns a rectangle representing the sprite image at the given index position. The index has to be
//     * between 0 and 239 which represents all possible 240 tiles in the 20 x 12 matrix.
//     *
//     * @param index Index of the tile to be retrieved as a rectangular viewport.
//     *
//     * @return Rectangle that represents the boundaries of the accessed tile.
//     *
//     * @throws RuntimeException if index is below 0 or above 239.
//     */
//    @SuppressWarnings("WeakerAccess")
//    protected Rectangle2D getViewport(int index) {
//
//        if (index < 0 || index > 239)
//            throw new SpriteSheetIndexOutOfBoundsException();
//
//        int horizontalIndex = index % 20;
//        int verticalIndex = index / 20;
//
//        return new Rectangle2D(horizontalIndex * WIDTH, verticalIndex * HEIGHT, WIDTH, HEIGHT);
//    }

    /**
     * Returns current value of stillImageIndex.
     *
     * @return current value of stillImageIndex.
     */
    public int getStillImageIndex() {

        return stillImageIndex.get();
    }

    /**
     * Sets the value of stillImageIndex.
     *
     * @param index future value of stillImageIndex.
     */
    @SuppressWarnings("WeakerAccess")
    public void setStillImageIndex(int index) {

        stillImageIndex.set(index);
//        setViewport(getViewport(stillImageIndex.get()));
    }

    /**
     * Returns the property object for the stillImageIndex.
     *
     * @return property object for the stillImageIndex.
     */
    public IntegerProperty stillImageIndexProperty() {

        return stillImageIndex;
    }
}
