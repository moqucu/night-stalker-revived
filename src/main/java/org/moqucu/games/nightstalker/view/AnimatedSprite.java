package org.moqucu.games.nightstalker.view;

import javafx.animation.Animation;
import javafx.beans.property.*;
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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final ObjectProperty<Indices> frameIndices;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final BooleanProperty autoReversible = new SimpleBooleanProperty(true);

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty frameDurationInMillis = new SimpleIntegerProperty(100);

    @SneakyThrows
    protected AnimatedSprite() {

        super();

        frameIndices = wrapIndicesInObjectProperty(Indices.builder().lower(0).upper(0).build());
    }

    private ObjectProperty<Indices> wrapIndicesInObjectProperty(Indices indices) {

        return new ObjectPropertyBase<>(indices) {

            Indices indicesObject = indices;

            @Override
            public Object getBean() {

                return indicesObject;
            }

            @Override
            public String getName() {

                return "indices";
            }
        };
    }

    /**
     * Calculates the number of frames based on the lower and upper index as provided by the #frameIndices property.
     *
     * @return Number of frames.
     */
    private int calculateNumberOfFrames() {

        return frameIndices.get().getUpper() - frameIndices.get().getLower() + 1;
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

    public Indices getFrameIndices() {

        return frameIndices.get();
    }

    public void setFrameIndices(Indices frameIndices) {

        this.frameIndices.set(frameIndices);
        configureAnimation();
    }

    public ObjectProperty<Indices> frameIndicesProperty() {

        return frameIndices;
    }

    public boolean isAutoReversible() {

        return autoReversible.get();
    }

    @SuppressWarnings("WeakerAccess")
    public void setAutoReversible(boolean autoReversible) {

        this.autoReversible.set(autoReversible);
        configureAnimation();
    }

    public BooleanProperty autoReversibleProperty() {

        return autoReversible;
    }

    public int getFrameDurationInMillis() {

        return frameDurationInMillis.get();
    }

    @SuppressWarnings("WeakerAccess")
    public void setFrameDurationInMillis(int frameDurationInMillis) {

        this.frameDurationInMillis.set(frameDurationInMillis);
        configureAnimation();
    }

    public IntegerProperty frameDurationInMillisProperty() {

        return frameDurationInMillis;
    }

    /**
     * Is being called while the sprite translateAnimation is progressing. Interpolates fraction between 0 and 1.
     * Then uses the fraction parameter to calculate the index of the frame that the viewport need to be set to.
     *
     * @param fraction Value between 0 and 1; progresses as the animations continues.
     */
    @SuppressWarnings("WeakerAccess")
    public void interpolate(Double fraction) {

        double indexAsDouble = frameIndices.get().getLower() + fraction * (calculateNumberOfFrames() - 1);
        setViewport(getViewport(Long.valueOf(Math.round(indexAsDouble)).intValue()));
    }

    protected void startAnimatingMe() {

        animation.setOnFinished(event -> startAnimatingMe());
        animation.playFromStart();
    }

    protected void stopAnimatingMe() {

        animation.setOnFinished(null);
        animation.stop();
    }
}
