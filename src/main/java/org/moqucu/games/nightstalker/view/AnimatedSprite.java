package org.moqucu.games.nightstalker.view;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import lombok.*;

import java.util.*;

@Data
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    private Animation animation;

    protected final List<Rectangle2D> frames = new LinkedList<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty numberOfFrames = new SimpleIntegerProperty(-1);

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty frameDuration = new SimpleIntegerProperty(100);

    protected AnimatedSprite() {

        super();
    }

    public int getNumberOfFrames() {

        return numberOfFrames.get();
    }

    protected void setNumberOfFrames(int numberOfFrames) {

        this.numberOfFrames.set(numberOfFrames);
        frames.clear();

        for (int i = 0; i < numberOfFrames; i++)
            frames.add(new Rectangle2D(i * WIDTH, 0, WIDTH, HEIGHT));

        setViewport(frames.get(0));

        animation = new Transition() {

            {
                setCycleDuration(Duration.millis(numberOfFrames * frameDuration.get()));
                setCycleCount(INDEFINITE);
                setAutoReverse(true);
            }

            @Override
            protected void interpolate(double fraction) {

                setViewport(frames.get(Math.round((numberOfFrames - 2) * (float) fraction) + 1));
            }
        };
    }

    public IntegerProperty numberOfFramesProperty() {

        return numberOfFrames;
    }

    public int getFrameDuration() {

        return frameDuration.get();
    }

    public void setFrameDuration(int frameDuration) {

        this.frameDuration.set(frameDuration);
    }

    public IntegerProperty frameDurationProperty() {

        return frameDuration;
    }

    protected void playAnimation() {

        animation.playFromStart();
    }

    public void stopAnimation() {

        animation.stop();
    }
}
