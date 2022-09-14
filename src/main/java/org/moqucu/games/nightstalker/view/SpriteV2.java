package org.moqucu.games.nightstalker.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.ObservableMap;
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

    @Getter
    @Delegate(types = GameObject.class)
    @SuppressWarnings("deprecation")
    protected GameObject model = new GameObjectImpl() {
    };

    @SneakyThrows
    public SpriteV2() {

        super();
        idProperty().bind(
                ReadOnlyJavaBeanStringPropertyBuilder
                        .create()
                        .name("objectId")
                        .bean(model)
                        .build()
        );
        JavaBeanBooleanPropertyBuilder
                .create()
                .name("objectVisible")
                .bean(model)
                .build()
                .bindBidirectional(visibleProperty());


        // setId(model.getObjectId());
        // setVisible(model.isObjectVisible());
        // ObservableMap<Object, Object> map = getProperties();
        model.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("initialImageIndex"))
                        setViewport(getViewport((Integer) evt.getNewValue()));
                }
        );
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

        int horizontalIndex = index % 20;
        int verticalIndex = index / 20;

        return new Rectangle2D(
                horizontalIndex * getWidth(),
                verticalIndex * getHeight(),
                getWidth(),
                getHeight()
        );
    }
}
