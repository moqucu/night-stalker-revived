package org.moqucu.games.nightstalker.view.object;

import javafx.animation.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.PropertyNames;
import org.moqucu.games.nightstalker.model.object.Lives;
import org.moqucu.games.nightstalker.view.Sprite;

import java.beans.PropertyChangeListener;

public class LivesLabel extends Text implements Sprite {

    @Getter
    private Lives model = new Lives();

    private final FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));

    private final PropertyChangeListener propertyChangeListener = evt -> {

        if (evt.getPropertyName().equals(PropertyNames.LIVES)) {

            setText(evt.getNewValue().toString());
            fadeTransition.play();
        }
    };

    private void initializeTextFromModel() {

        setText(Integer.toString(model.getLives()));
        setVisible(false);
    }

    @SneakyThrows
    private void bindProperties() {

        initializeTextFromModel();
        model.addPropertyChangeListener(propertyChangeListener);
    }


    private void unbindProperties() {

        model.removePropertyChangeListener(propertyChangeListener);
        setText("");
    }

    public LivesLabel() {

        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        bindProperties();
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof Lives))
            throw new RuntimeException("Model needs to be of type " + Lives.class
                    + " but was of type " + model.getClass() + "!");
        else {

            unbindProperties();
            this.model = (Lives) model;
            bindProperties();
        }
    }
}
