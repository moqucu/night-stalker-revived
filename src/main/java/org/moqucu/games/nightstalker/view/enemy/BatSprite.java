package org.moqucu.games.nightstalker.view.enemy;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.adapter.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.enemy.Bat;
import org.moqucu.games.nightstalker.view.MovableSprite;

public class BatSprite extends MovableSprite {

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

    private void setBatModel(Bat model) {

        unbindProperties();
        super.setModel(model);
        this.model = model;
        bindProperties(getModel());
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof Bat))
            throw new RuntimeException("Game object needs to be of class Bat!");

        setBatModel((Bat)gameObject);
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
