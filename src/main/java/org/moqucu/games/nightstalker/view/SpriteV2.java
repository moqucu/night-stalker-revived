package org.moqucu.games.nightstalker.view;

import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameObjectImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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

        JavaBeanDoublePropertyBuilder
                .create()
                .name("XPosition")
                .bean(model)
                .build()
                .bindBidirectional(xProperty());

        JavaBeanDoublePropertyBuilder
                .create()
                .name("YPosition")
                .bean(model)
                .build()
                .bindBidirectional(yProperty());

        model.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("initialImageIndex"))
                        setViewport(getViewport((Integer) evt.getNewValue()));
                    else if (evt.getPropertyName().equals("imageMapFileName")) {
                        if (evt.getNewValue() == null || evt.getNewValue().equals(""))
                            setImage(null);
                        try (InputStream inputStream = getClass().getResourceAsStream((String) evt.getNewValue())) {
                            setImage(new Image(Objects.requireNonNull(inputStream)));
                        } catch (IOException | NullPointerException ioException) {
                            setImage(null);
                        }
                    }
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
