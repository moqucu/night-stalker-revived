package org.moqucu.games.nightstalker.view;

import javafx.animation.Animation;
import javafx.beans.property.*;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.CustomTransition;
import org.moqucu.games.nightstalker.model.Indices;

import static javafx.animation.Animation.INDEFINITE;

@Data
@Log4j2
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    private Animation animation;

    //todo
    protected ObjectProperty<Indices> frameIndices;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected final BooleanProperty autoReversible = new SimpleBooleanProperty(true);

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty frameDurationInMillis = new SimpleIntegerProperty(100);

    protected AnimatedSprite() {

        super();
    }

    private int calculateNumberOfFrames() {

        return 0;
    }

    @SneakyThrows
    private void configureAnimation() {

        animation = new CustomTransition(
                Duration.millis(calculateNumberOfFrames() * frameDurationInMillis.get()),
                INDEFINITE,
                autoReversible.get(),
                this,
                this.getClass().getMethod("interpolate", Double.class)
        );
    }

    public boolean isAutoReversible() {

        return autoReversible.get();
    }

    protected void setAutoReversible(boolean autoReversible) {

        this.autoReversible.set(autoReversible);
        frames.clear();

        for (int i = 0; i < autoReversible; i++)
            frames.add(new Rectangle2D(i * WIDTH, 0, WIDTH, HEIGHT));

        setViewport(frames.get(0));

    }

    public BooleanProperty autoReversibleProperty() {

        return autoReversible;
    }

    public int getFrameDurationInMillis() {

        return frameDurationInMillis.get();
    }

    protected void setFrameDurationInMillis(int frameDurationInMillis) {

        this.frameDurationInMillis.set(frameDurationInMillis);
        configureAnimation();
    }

    public IntegerProperty frameDurationInMillisProperty() {

        return frameDurationInMillis;
    }

    public void interpolate(Double fraction) {

        setViewport(
                getViewport(Long.valueOf(Math.round((calculateNumberOfFrames() - 2) * fraction)).intValue() + 1)
        );
    }

    protected void playAnimation() {

        animation.playFromStart();
    }

    protected void stopAnimation() {

        animation.stop();
    }
}
