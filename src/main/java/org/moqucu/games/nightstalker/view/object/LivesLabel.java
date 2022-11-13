package org.moqucu.games.nightstalker.view.object;

import javafx.animation.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.object.Lives;
import org.moqucu.games.nightstalker.view.Sprite;

public class LivesLabel extends Text implements Sprite {

    @Getter
    private Lives model = new Lives();

    private final FadeTransition fadeTransition;

    public LivesLabel(FadeTransition fadeTransition) {

        this.fadeTransition = fadeTransition;

        setText(Integer.toString(model.getLives()));
        setVisible(false);

        model.addPropertyChangeListener(evt -> {

            if (evt.getPropertyName().equals("lives")) {

                setText(evt.getNewValue().toString());
                fadeTransition.play();
            }
        });
    }

    @SuppressWarnings("unused")
    public LivesLabel() {

        this(new FadeTransition(Duration.millis(2000)));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
    }

    @Override
    public void setModel(GameObject model) {

        if (!(model instanceof Lives))
            throw new RuntimeException("Model needs to be of type " + Lives.class
                    + "but was of type " + model.getClass() + "!");
        else
            this.model = (Lives) model;
    }
}
