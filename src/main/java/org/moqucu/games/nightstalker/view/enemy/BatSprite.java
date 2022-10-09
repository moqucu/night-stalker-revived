package org.moqucu.games.nightstalker.view.enemy;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.adapter.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.enemy.Bat;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;

public class BatSprite extends MovableSpriteV2 {

    @Getter
    private Bat model;

    private JavaBeanDoubleProperty sleepTimeInMillisProperty;

    private JavaBeanObjectProperty<Direction> directionProperty;

    @SneakyThrows
    private void bindProperties(Bat model) {

        sleepTimeInMillisProperty = JavaBeanDoublePropertyBuilder
                .create()
                .name("sleepTime")
                .bean(model)
                .build();

        //noinspection unchecked
        directionProperty = JavaBeanObjectPropertyBuilder
                .create()
                .name("direction")
                .bean(model)
                .build();
    }

    public BatSprite() {

        super(new Bat());
        model = (Bat) super.getModel();
        bindProperties(getModel());
    }

    public void setModel(Bat model) {

        super.setModel(model);
        this.model = model;
        bindProperties(getModel());
    }

    public void setSleepTimeInMillis(double sleepTime) {

        sleepTimeInMillisProperty.set(sleepTime);
    }

    public double getSleepTimeInMillis() {

        return sleepTimeInMillisProperty.get();
    }

    public DoubleProperty sleepTimeInMillis() {

        return sleepTimeInMillisProperty;
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
