package org.moqucu.games.nightstalker.view;

import javafx.animation.Animation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.CustomTransition;

import java.util.*;

import static javafx.animation.Animation.INDEFINITE;

@Data
@SuppressWarnings("unused")
@Log4j2
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    private Animation animation;

    protected final List<Rectangle2D> frames = new LinkedList<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected final IntegerProperty numberOfFrames = new SimpleIntegerProperty(-1);

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty frameDuration = new SimpleIntegerProperty(100);

    protected AnimatedSprite() {

        super();
    }

    public int getNumberOfFrames() {

        return numberOfFrames.get();
    }

    @SneakyThrows
    protected void setNumberOfFrames(int numberOfFrames) {

        this.numberOfFrames.set(numberOfFrames);
        frames.clear();

        for (int i = 0; i < numberOfFrames; i++)
            frames.add(new Rectangle2D(i * WIDTH, 0, WIDTH, HEIGHT));

        setViewport(frames.get(0));

        animation = new CustomTransition(
                Duration.millis(numberOfFrames * frameDuration.get()),
                INDEFINITE,
                true,
                this,
                this.getClass().getMethod("interpolate", Double.class)
        );
    }

    @SuppressWarnings("WeakerAccess")
    public void interpolate(Double fraction) {

        setViewport(frames.get(Long.valueOf(Math.round((numberOfFrames.get() - 2) * fraction)).intValue() + 1));
    }

    public IntegerProperty numberOfFramesProperty() {

        return numberOfFrames;
    }

    public int getFrameDuration() {

        return frameDuration.get();
    }

    protected void setFrameDuration(int frameDuration) {

        this.frameDuration.set(frameDuration);
    }

    public IntegerProperty frameDurationProperty() {

        return frameDuration;
    }

    protected void playAnimation() {

        animation.playFromStart();
    }

    protected void stopAnimation() {

        animation.stop();
    }
}
