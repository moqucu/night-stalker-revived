package org.moqucu.games.nightstalker.view;

import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.GameObject;

import java.beans.PropertyChangeListener;

public class AnimatedSprite extends DisplayableSprite {

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

    private void setAnimatedObjectModel(AnimatedObject model) {

        super.setModel(model);
        unbindProperties();
        this.model = model;
        bindProperties(model);
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof AnimatedObject))
            throw new RuntimeException("Model needs to be of class AnimatedObject!");
        setAnimatedObjectModel((AnimatedObject) model);
    }
}
