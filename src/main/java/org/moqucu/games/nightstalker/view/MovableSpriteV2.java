package org.moqucu.games.nightstalker.view;

import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MovableObject;

public class MovableSpriteV2 extends AnimatedSpriteV2 {

    @Getter
    private MovableObject model;

    private JavaBeanObjectProperty<Direction> directionProperty;

    @SneakyThrows
    private void bindProperties(MovableObject model) {

        //noinspection unchecked
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

    public MovableSpriteV2() {

        super(new MovableObject(){});
        model = (MovableObject) super.getModel();
        bindProperties(model);
    }

    public MovableSpriteV2(MovableObject model) {

        super(model);
        this.model = model;
        bindProperties(model);
    }

    public void setModel(MovableObject model) {

        unbindProperties();
        super.setModel(model);
        this.model = model;
        bindProperties(model);
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
