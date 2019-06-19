package org.moqucu.games.nightstalker.sprite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.utility.MazeNotFoundException;
import org.moqucu.games.nightstalker.utility.SpriteSheetIndexOutOfBoundsException;
import org.moqucu.games.nightstalker.view.Maze;
import org.springframework.statemachine.config.StateMachineConfig;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.config.common.annotation.ObjectPostProcessor;

/**
 * Represents a basic sprite class that specializes ImageView.
 */
@Data
@Log4j2
@SuppressWarnings("ALL")
@EqualsAndHashCode(callSuper = true)
public abstract class Sprite<S, E> extends ImageView implements StateMachineConfigurer<S, E> {

    public static final double WIDTH = 32.0;

    public static final double HEIGHT = 32.0;

    private StateMachineModelBuilder<S, E> modelBuilder;
    private StateMachineTransitionBuilder<S, E> transitionBuilder;
    private StateMachineStateBuilder<S, E> stateBuilder;
    private StateMachineConfigurationBuilder<S, E> configurationBuilder;

    /**
     * Represents the cell index of the image tile that shall be used for rendering purposes. Default value is 0.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private IntegerProperty stillImageIndex = new SimpleIntegerProperty(0);

    /**
     * Constructor that sets stillImageIndex based on standard initialization value (= 0).
     */
    public Sprite() {

        super();
        setStillImageIndex(stillImageIndex.get());
    }

    /**
     * Returns a rectangle representing the sprite image at the given index position. The index has to be
     * between 0 and 239 which represents all possible 240 tiles in the 20 x 12 matrix.
     *
     * @param index Index of the tile to be retrieved as a rectangular viewport.
     *
     * @return Rectangle that represents the boundaries of the accessed tile.
     *
     * @throws SpriteSheetIndexOutOfBoundsException if index is below 0 or above 239.
     */
    @SuppressWarnings("WeakerAccess")
    protected Rectangle2D getViewport(int index) {

        if (index < 0 || index > 239)
            throw new SpriteSheetIndexOutOfBoundsException();

        int horizontalIndex = index % 20;
        int verticalIndex = index / 20;

        return new Rectangle2D(horizontalIndex * WIDTH, verticalIndex * HEIGHT, WIDTH, HEIGHT);
    }

    /**
     * Returns current value of stillImageIndex.
     *
     * @return current value of stillImageIndex.
     */
    public int getStillImageIndex() {

        return stillImageIndex.get();
    }

    /**
     * Sets the value of stillImageIndex.
     *
     * @param index future value of stillImageIndex.
     */
    @SuppressWarnings("WeakerAccess")
    public void setStillImageIndex(int index) {

        stillImageIndex.set(index);
        setViewport(getViewport(stillImageIndex.get()));
    }

    /**
     * Returns the property object for the stillImageIndex.
     *
     * @return property object for the stillImageIndex.
     */
    public IntegerProperty stillImageIndexProperty() {

        return stillImageIndex;
    }

    /**
     * Traverses the parent objects of this sprite and returns the Maze object, if found.
     *
     * @return Maze object if found.
     *
     * @throws MazeNotFoundException if no Maze object could be found as part of parent hierarchy traversal.
     */
    protected Maze getMaze() {

        boolean foundParentOrNothing = false;
        Parent parent = this.getParent();
        while (!foundParentOrNothing) {

            if (parent instanceof Maze || parent == null)
                foundParentOrNothing = true;
            else
                parent = parent.getParent();
        }

        if (parent == null)
            throw new MazeNotFoundException();

        return (Maze)parent;
    }

    public final void init(StateMachineConfigBuilder<S, E> config) throws Exception {
        config.setSharedObject(StateMachineModelBuilder.class, this.getStateMachineModelBuilder());
        config.setSharedObject(StateMachineTransitionBuilder.class, this.getStateMachineTransitionBuilder());
        config.setSharedObject(StateMachineStateBuilder.class, this.getStateMachineStateBuilder());
        config.setSharedObject(StateMachineConfigurationBuilder.class, this.getStateMachineConfigurationBuilder());
    }

    @Override
    public void configure(StateMachineConfigBuilder<S, E> config) {
    }

    @Override
    public void configure(StateMachineModelConfigurer<S, E> model) {
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<S, E> config) {
    }

    @Override
    public void configure(StateMachineStateConfigurer<S, E> states) {
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<S, E> transitions) {
    }

    @Override
    public boolean isAssignable(AnnotationBuilder<StateMachineConfig<S, E>> builder) {

        return builder instanceof StateMachineConfigBuilder;
    }

    private StateMachineModelBuilder<S, E> getStateMachineModelBuilder() {
        if (this.modelBuilder != null) {
            return this.modelBuilder;
        } else {
            this.modelBuilder = new StateMachineModelBuilder(
                    ObjectPostProcessor.QUIESCENT_POSTPROCESSOR,
                    true
            );
            this.configure(this.modelBuilder);
            return this.modelBuilder;
        }
    }

    private StateMachineTransitionBuilder<S, E> getStateMachineTransitionBuilder() {

        if (this.transitionBuilder != null) {
            return this.transitionBuilder;
        } else {
            this.transitionBuilder = new StateMachineTransitionBuilder(
                    ObjectPostProcessor.QUIESCENT_POSTPROCESSOR,
                    true
            );
            this.configure(this.transitionBuilder);
            return this.transitionBuilder;
        }
    }

    private StateMachineStateBuilder<S, E> getStateMachineStateBuilder() {

        if (this.stateBuilder != null) {
            return this.stateBuilder;
        } else {
            this.stateBuilder = new StateMachineStateBuilder(
                    ObjectPostProcessor.QUIESCENT_POSTPROCESSOR,
                    true)
            ;
            this.configure(this.stateBuilder);
            return this.stateBuilder;
        }
    }

    private StateMachineConfigurationBuilder<S, E> getStateMachineConfigurationBuilder() throws Exception {
        if (this.configurationBuilder != null) {
            return this.configurationBuilder;
        } else {
            this.configurationBuilder = new StateMachineConfigurationBuilder(
                    ObjectPostProcessor.QUIESCENT_POSTPROCESSOR,
                    true
            );
            this.configure(this.configurationBuilder);
            return this.configurationBuilder;
        }
    }
}
