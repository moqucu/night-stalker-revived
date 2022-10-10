package org.moqucu.games.nightstalker.view.enemy;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.adapter.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.enemy.Bat;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;

public class BatSprite extends MovableSpriteV2 {

    @Getter
    private Bat model;

    private JavaBeanDoubleProperty sleepTimeInMillisProperty;

    @SneakyThrows
    private void bindProperties(Bat model) {

        sleepTimeInMillisProperty = JavaBeanDoublePropertyBuilder
                .create()
                .name("sleepTime")
                .bean(model)
                .build();
    }

    @SneakyThrows
    private void unbindProperties() {

        if (sleepTimeInMillisProperty != null) {

            if(sleepTimeInMillisProperty.isBound())
                sleepTimeInMillisProperty.unbind();

            sleepTimeInMillisProperty.dispose();
            sleepTimeInMillisProperty = null;
        }
     }

    public BatSprite() {

        super(new Bat());
        model = (Bat) super.getModel();
        bindProperties(getModel());
    }

    public void setModel(Bat model) {

        unbindProperties();
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
}
