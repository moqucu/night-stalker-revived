package org.moqucu.games.nightstalker.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.adapter.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.GameObject;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Represents a basic sprite class that specializes ImageView.
 */
@Log4j2
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite extends ImageView {

    @Getter
    protected GameObject model = new GameObject() {
    };

    private ReadOnlyJavaBeanStringProperty objectIdProperty;

    private JavaBeanBooleanProperty objectVisibleProperty;

    private JavaBeanDoubleProperty xPositionProperty;

    private JavaBeanDoubleProperty yPositionProperty;

    private JavaBeanIntegerProperty initialImageIndexProperty;

    private final PropertyChangeListener propertyChangeListener = evt -> {

        if (evt.getPropertyName().equals("initialImageIndex"))
            initializeViewPortFromInitialImageIndex((Integer) evt.getNewValue());
        else if (evt.getPropertyName().equals("imageMapFileName"))
            initializeImageFromImageMapFileName((String) evt.getNewValue());
    };

    private void initializeViewPortFromInitialImageIndex(int initialImageIndex) {

        setViewport(getViewport(initialImageIndex));
    }

    private void initializeImageFromImageMapFileName(String imageMapFileName) {

        if (imageMapFileName == null || imageMapFileName.equals(""))
            setImage(null);

        Objects.requireNonNull(imageMapFileName);
        try (InputStream inputStream = getClass().getResourceAsStream(imageMapFileName)) {
            setImage(new Image(Objects.requireNonNull(inputStream)));
        } catch (IOException | NullPointerException ioException) {
            setImage(null);
        }
    }

    @SneakyThrows
    private void bindProperties(GameObject model) {

        objectIdProperty = ReadOnlyJavaBeanStringPropertyBuilder
                .create()
                .name("objectId")
                .bean(model)
                .build();
        idProperty().bind(objectIdProperty);

        objectVisibleProperty = JavaBeanBooleanPropertyBuilder
                .create()
                .name("objectVisible")
                .bean(model)
                .build();
        objectVisibleProperty.bindBidirectional(visibleProperty());
        xPositionProperty = JavaBeanDoublePropertyBuilder
                .create()
                .name("XPosition")
                .bean(model)
                .build();
        xProperty().bindBidirectional(xPositionProperty);

        yPositionProperty = JavaBeanDoublePropertyBuilder
                .create()
                .name("YPosition")
                .bean(model)
                .build();
        yProperty().bindBidirectional(yPositionProperty);

        initialImageIndexProperty = JavaBeanIntegerPropertyBuilder
                .create()
                .name("initialImageIndex")
                .bean(model)
                .build();

        initializeImageFromImageMapFileName(model.getImageMapFileName());
        initializeViewPortFromInitialImageIndex(model.getInitialImageIndex());

        model.addPropertyChangeListener(propertyChangeListener);
    }

    private void unbindProperties() {

        model.removePropertyChangeListener(propertyChangeListener);

        idProperty().unbind();
        if (objectIdProperty != null) {

            objectIdProperty.dispose();
            objectIdProperty = null;
        }

        visibleProperty().unbindBidirectional(objectVisibleProperty);
        if (objectVisibleProperty != null) {

            objectVisibleProperty.unbind();
            objectVisibleProperty.dispose();
            objectVisibleProperty = null;
        }

        xProperty().unbindBidirectional(xPositionProperty);
        if (xPositionProperty != null) {

            xPositionProperty.unbind();
            xPositionProperty.dispose();
            xPositionProperty = null;
        }

        yProperty().unbindBidirectional(yPositionProperty);
        if (yPositionProperty != null) {

            yPositionProperty.unbind();
            yPositionProperty.dispose();
            yPositionProperty = null;
        }

        if (initialImageIndexProperty.isBound())
            initialImageIndexProperty.unbind();
        if (initialImageIndexProperty != null) {

            initialImageIndexProperty.dispose();
            initialImageIndexProperty = null;
        }

        setImage(null);
        setViewport(getViewport(0));
    }

    public Sprite() {

        super();
        bindProperties(model);
    }

    protected Sprite(GameObject model) {

        super();
        this.model = model;
        bindProperties(model);
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
                horizontalIndex * model.getWidth(),
                verticalIndex * model.getHeight(),
                model.getWidth(),
                model.getHeight()
        );
    }

    public void setModel(GameObject model) {

        unbindProperties();
        this.model = model;
        bindProperties(model);
    }

    public IntegerProperty initialImageIndexProperty() {

        return initialImageIndexProperty;
    }

    public int getInitialImageIndex() {

        return initialImageIndexProperty.get();
    }

    public void setInitialImageIndex(int initialImageIndex) {

        initialImageIndexProperty.set(initialImageIndex);
    }
}
