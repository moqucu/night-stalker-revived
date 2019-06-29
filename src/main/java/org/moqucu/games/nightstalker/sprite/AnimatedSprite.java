package org.moqucu.games.nightstalker.sprite;

import javafx.animation.Animation;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.utility.CustomTransition;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.HashMap;
import java.util.Map;

import static javafx.animation.Animation.INDEFINITE;

@Data
@Log4j2
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite implements StateMachineListener {

    @Data
    @Builder
    static protected class Indices {

        private int lower;
        private int upper;
    }

    @Data
    @Builder
    static protected class AnimationProperty {

        private Indices frameIndices;
        private Boolean autoReversible;
        private Integer frameDurationInMillis;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final ObjectProperty<Map<Enum, AnimationProperty>> animationProperties;

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
        animationProperties = wrapAnimationPropertiesInObjectProperty(new HashMap<>());
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

    private ObjectProperty<Map<Enum, AnimationProperty>> wrapAnimationPropertiesInObjectProperty(
            Map<Enum, AnimationProperty> animationProperties
    ) {

        return new ObjectPropertyBase<>(animationProperties) {

            Map<Enum, AnimationProperty> animationPropertiesObject = animationProperties;

            @Override
            public Object getBean() {

                return animationPropertiesObject;
            }

            @Override
            public String getName() {

                return "animationProperties";
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

    public Map<Enum, AnimationProperty> getAnimationProperties() {

        return animationProperties.get();
    }

    public void setAnimationProperties(Map<Enum, AnimationProperty> animationProperties) {

        this.animationProperties.set(animationProperties);
        configureAnimation();
    }

    public ObjectProperty<Map<Enum, AnimationProperty>> getAnimationPropertiesProperty() {

        return animationProperties;
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

    protected void animateMeFromStart() {

        animation.setOnFinished(event -> animateMeFromStart());
        animation.playFromStart();
    }

    protected void stopAnimatingMe() {

        animation.setOnFinished(null);
        animation.stop();
    }

    protected Point2D getCurrentLocation() {

        return new Point2D(getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
    }

    @Override
    public void stateChanged(State state, State state1) {
    }

    @Override
    public void stateEntered(State state) {
    }

    @Override
    public void stateExited(State state) {
    }

    @Override
    public void eventNotAccepted(Message message) {
    }

    @Override
    public void transition(Transition transition) {
    }

    @Override
    public void transitionStarted(Transition transition) {
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public void transitionEnded(Transition transition) {

        log.debug("My state changed to {}", transition.getTarget().getId());
        stopAnimatingMe();
        if (getAnimationProperties().containsKey(transition.getTarget().getId())) {

            setFrameIndices(getAnimationProperties().get(transition.getTarget().getId()).getFrameIndices());
            setAutoReversible(getAnimationProperties().get(transition.getTarget().getId()).getAutoReversible());
            setFrameDurationInMillis(
                    getAnimationProperties().get(transition.getTarget().getId()).getFrameDurationInMillis()
            );
        }
        animateMeFromStart();
    }

    @Override
    public void stateMachineStarted(StateMachine stateMachine) {
    }

    @Override
    public void stateMachineStopped(StateMachine stateMachine) {
    }

    @Override
    public void stateMachineError(StateMachine stateMachine, Exception e) {
    }

    @Override
    public void extendedStateChanged(Object o, Object o1) {
    }

    @Override
    public void stateContext(StateContext stateContext) {
    }
}
