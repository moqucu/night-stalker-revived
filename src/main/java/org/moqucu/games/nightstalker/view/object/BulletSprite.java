package org.moqucu.games.nightstalker.view.object;

import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.model.object.Bullet;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.view.DisplayableSprite;
import org.moqucu.games.nightstalker.view.Sprite;

import java.beans.PropertyChangeListener;

public class BulletSprite extends DisplayableSprite implements Sprite {

    @Getter
    private Bullet model;

    private final PropertyChangeListener propertyChangeListener = evt -> {};

    @SneakyThrows
    private void bindProperties(DisplayableObject model) {

        model.addPropertyChangeListener(propertyChangeListener);
    }

    private void unbindProperties() {

        model.removePropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof Bullet))
            throw new RuntimeException("Model needs to be of type " + Bullet.class
                    + " but was of type " + model.getClass() + "!");
        else {

            unbindProperties();
            super.setModel(model);
            this.model = (Bullet) model;
            bindProperties(getModel());
        }
    }

    public BulletSprite() {

        super(new Bullet());
        model = (Bullet) super.getModel();
        bindProperties(getModel());
    }
}
