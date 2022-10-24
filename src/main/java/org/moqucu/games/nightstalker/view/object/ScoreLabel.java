package org.moqucu.games.nightstalker.view.object;

import javafx.animation.FadeTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.moqucu.games.nightstalker.utility.HitListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ScoreLabel extends Text implements HitListener {

    private Map<Class<?>, Long> pointMap = new HashMap<>();

    private LongProperty score = new SimpleLongProperty(0L);

    private FadeTransition fadeTransition;

    public ScoreLabel() {

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/intellect.ttf");
        setFont(Font.loadFont(stream, 40.0));

        fadeTransition = new FadeTransition(Duration.millis(2000), this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);

//        pointMap.put(Spider.class, 100L);
//        pointMap.put(GreyRobot.class, 300L);
    }

    @Override
    public void objectHit(Object object) {

        score.set(score.get() + pointMap.get(object.getClass()));
        setText(Long.toString(score.get()));
        fadeTransition.play();
    }
}
