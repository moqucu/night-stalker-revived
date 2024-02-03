package org.moqucu.games.nightstalker.view;

import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.MovableObject;

public class MovableSprite extends AnimatedSprite {

    @Getter
    private MovableObject model;

    private JavaBeanObjectProperty<Direction> directionProperty;

    @SneakyThrows
    @SuppressWarnings("unchecked") // Reason: Bad API
    private void bindProperties(MovableObject model) {

        directionProperty = JavaBeanObjectPropertyBuilder
                .create()
                .name("direction")
                .bean(model)
                .build();
    }

    private void unbindProperties() {

        if (directionProperty != null) {

            if (directionProperty.isBound())
                directionProperty().unbind();

            directionProperty.dispose();
            directionProperty = null;
        }
    }

    public MovableSprite() {

        super(new MovableObject(){});
        model = (MovableObject) super.getModel();
        bindProperties(model);
    }

    public MovableSprite(MovableObject model) {

        super(model);
        this.model = model;
        bindProperties(model);
    }

    private void setMovableObjectModel(MovableObject model) {

        unbindProperties();
        super.setModel(model);
        this.model = model;
        bindProperties(model);
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof MovableObject))
            throw new RuntimeException("Model needs to be of class MovableObject!");
        setMovableObjectModel((MovableObject) model);
    }

    public void setDirection(Direction direction) {

        directionProperty.set(direction);
    }

    public Direction getDirection() {

        return directionProperty.get();
    }

    public JavaBeanObjectProperty<Direction> directionProperty() {

        return directionProperty;
    }
}
