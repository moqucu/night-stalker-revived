package org.moqucu.games.nightstalker.label;

import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LivesLabel extends Text {

    private IntegerProperty lives = new SimpleIntegerProperty();

    private FadeTransition fadeTransition;

    public LivesLabel() {

        setFont(Font.loadFont("fonts/intellect.ttf", 40.0));

        fadeTransition = new FadeTransition(Duration.millis(2000), this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);

        lives.addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {

                setText(newValue.toString());
                fadeTransition.play();
            }
        });
    }

    public void bindLivesProperty(IntegerProperty livesPropertyToBeBound) {

        this.lives.bind(livesPropertyToBeBound);
    }
}
