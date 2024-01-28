package org.moqucu.games.nightstalker.view.object;

import javafx.beans.property.adapter.*;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.model.object.Bullet;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.view.Sprite;

import java.beans.PropertyChangeListener;

public class BulletRectangle extends Rectangle implements Sprite {

    @Getter
    private Bullet model = new Bullet();

    private ReadOnlyJavaBeanStringProperty objectIdProperty;

    private JavaBeanBooleanProperty objectVisibleProperty;

    private JavaBeanDoubleProperty xPositionProperty;

    private JavaBeanDoubleProperty yPositionProperty;

    private final PropertyChangeListener propertyChangeListener = evt -> {};

    @SneakyThrows
    private void bindProperties(DisplayableObject model) {

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
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof Bullet))
            throw new RuntimeException("Model needs to be of type " + Bullet.class
                    + " but was of type " + model.getClass() + "!");
        else {

            unbindProperties();
            this.model = (Bullet) model;
            bindProperties(this.model);
        }
    }

    public BulletRectangle() {

        super();
        bindProperties(model);
    }
}
