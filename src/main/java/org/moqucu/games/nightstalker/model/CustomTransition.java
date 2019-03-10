package org.moqucu.games.nightstalker.model;

import javafx.animation.Transition;
import javafx.util.Duration;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.view.AnimatedSprite;

import java.lang.reflect.Method;

/**
 * Custom JavaFX transition for calling a method based on reflection and initializing it through Constructor.
 */
@Log4j2
public class CustomTransition extends Transition {

    private Method interpolateMethod;
    private AnimatedSprite caller;

    public CustomTransition(
            Duration cycleDuration,
            Integer cycleCount,
            Boolean autoReverse, AnimatedSprite caller,
            Method interpolateMethod
    ) {

        setCycleDuration(cycleDuration);
        setCycleCount(cycleCount);
        setAutoReverse(autoReverse);
        this.caller = caller;
        this.interpolateMethod = interpolateMethod;
    }

    @Override
    @SneakyThrows
    protected void interpolate(double fraction) {

        interpolateMethod.invoke(caller, fraction);
    }
}
