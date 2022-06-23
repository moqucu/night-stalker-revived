package org.moqucu.games.nightstalker.label;

import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.InputStream;

public class LivesLabel extends Text {

    private IntegerProperty lives = new SimpleIntegerProperty();

    private FadeTransition fadeTransition;

    private FadeTransition fadeTransition2;

    private FadeTransition fadeTransition3;

    public LivesLabel() {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/intellect.ttf");
        setFont(Font.loadFont(stream, 40.0));

        fadeTransition = new FadeTransition(Duration.millis(100), this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);

        fadeTransition2 = new FadeTransition(Duration.millis(2000), this);
        fadeTransition2.setFromValue(1.0);
        fadeTransition2.setToValue(1.0);
        fadeTransition2.setCycleCount(2);

        fadeTransition3 = new FadeTransition(Duration.millis(100), this);
        fadeTransition3.setFromValue(1.0);
        fadeTransition3.setToValue(0.0);
        fadeTransition3.setCycleCount(2);

        lives.addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {

                setText(newValue.toString());
                fadeTransition.play();
                fadeTransition2.play();
                fadeTransition3.play();
            }
        });
    }

    public void bindLivesProperty(IntegerProperty livesPropertyToBeBound) {

        this.lives.bind(livesPropertyToBeBound);
    }
}
