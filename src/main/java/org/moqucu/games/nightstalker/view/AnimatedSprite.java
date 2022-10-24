package org.moqucu.games.nightstalker.view;

import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.AnimatedObject;

import java.beans.PropertyChangeListener;

public abstract class AnimatedSprite extends Sprite {

    @Getter
    private AnimatedObject model = new AnimatedObject() {
    };

    private final PropertyChangeListener propertyChangeListener = evt -> {

        if (evt.getPropertyName().equals("imageIndex"))
            initializeViewPortFromImageIndex((Integer) evt.getNewValue());
    };

    private void initializeViewPortFromImageIndex(int imageIndex) {

        setViewport(getViewport(imageIndex));
    }

    @SneakyThrows
    private void bindProperties(AnimatedObject model) {

        initializeViewPortFromImageIndex(model.getImageIndex());
        model.addPropertyChangeListener(propertyChangeListener);
    }


    private void unbindProperties() {

        model.removePropertyChangeListener(propertyChangeListener);
        setImage(null);
        setViewport(getViewport(model.getInitialImageIndex()));
    }

    public AnimatedSprite() {
        super();
        bindProperties(model);
    }

    protected AnimatedSprite(AnimatedObject model) {

        super(model);
        this.model = model;
        bindProperties(model);
    }

    public void setModel(AnimatedObject model) {

        super.setModel(model);
        unbindProperties();
        this.model = model;
        bindProperties(model);
    }
}
