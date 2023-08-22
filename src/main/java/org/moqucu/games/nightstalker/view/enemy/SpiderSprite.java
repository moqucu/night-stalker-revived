package org.moqucu.games.nightstalker.view.enemy;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.enemy.Spider;
import org.moqucu.games.nightstalker.view.MovableSprite;

public class SpiderSprite extends MovableSprite {

    @Getter
    private Spider model;

    private JavaBeanBooleanProperty inMotionProperty;

    private JavaBeanBooleanProperty animatedProperty;

    @SneakyThrows
    private void bindProperties(Spider model) {

        inMotionProperty = JavaBeanBooleanPropertyBuilder
                .create()
                .name("inMotion")
                .bean(model)
                .build();

        animatedProperty = JavaBeanBooleanPropertyBuilder
                .create()
                .name("animated")
                .bean(model)
                .build();
    }

    @SneakyThrows
    private void unbindProperties() {

        if (inMotionProperty != null) {

            if(inMotionProperty.isBound())
                inMotionProperty.unbind();

            inMotionProperty.dispose();
            inMotionProperty = null;
        }

        if (animatedProperty != null) {

            if(animatedProperty.isBound())
                animatedProperty.unbind();

            animatedProperty.dispose();
            animatedProperty = null;
        }
    }

    public SpiderSprite() {

        super(new Spider());
        model = (Spider) super.getModel();
        bindProperties(getModel());
    }

    public void setModel(Spider model) {

        unbindProperties();
        super.setModel(model);
        this.model = model;
        bindProperties(getModel());
    }

    public boolean isInMotion() {

        return inMotionProperty.get();
    }

    public BooleanProperty inMotion() {

        return inMotionProperty;
    }

    public boolean isAnimated() {

        return animatedProperty.get();
    }

    public BooleanProperty animated() {

        return animatedProperty;
    }
}
