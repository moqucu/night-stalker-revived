package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.TimeListener;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameWorld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnimatedObjectTest {

    private final AnimatedObject animatedObject = new AnimatedObject() {
    };

    @Test
    @DisplayName("Inherits from game object.")
    public void hasInheritedFromGameObject() {

        assertThat(animatedObject, isA(GameObject.class));
    }

    @Test
    @DisplayName("Has frame rate property.")
    public void hasFrameRateProperty() {

        assertThat(animatedObject, hasProperty("frameRate"));
    }

    @Test
    @DisplayName("Frame rate property is of type integer.")
    public void frameRatePropertyOfTypeInteger() {

        assertThat(animatedObject.getFrameRate(), isA(Integer.class));
    }

    @Test
    @DisplayName("Has animated property.")
    public void hasAnimatedProperty() {

        assertThat(animatedObject, hasProperty("animated"));
    }

    @Test
    @DisplayName("Animated property is of type boolean.")
    public void animatedPropertyOfTypeBoolean() {

        assertThat(animatedObject.isAnimated(), isA(Boolean.class));
    }

    @Test
    @DisplayName("Has lower animation index property.")
    public void hasLowerAnimationIndexProperty() {

        assertThat(animatedObject, hasProperty("lowerAnimationIndex"));
    }

    @Test
    @DisplayName("Lower animation index property is of type integer.")
    public void lowerAnimationIndexPropertyOfTypeInteger() {

        assertThat(animatedObject.getLowerAnimationIndex(), isA(Integer.class));
    }

    @Test
    @DisplayName("Has upper animation index property.")
    public void hasUpperAnimationIndexProperty() {

        assertThat(animatedObject, hasProperty("upperAnimationIndex"));
    }

    @Test
    @DisplayName("Upper animation index property is of type integer.")
    public void upperAnimationIndexPropertyOfTypeInteger() {

        assertThat(animatedObject.getUpperAnimationIndex(), isA(Integer.class));
    }

    @Test
    @DisplayName("Cannot animate object without correctly setting frame rate and boundaries.")
    public void cannotAnimateObjectWithoutCorrectlySettingFrameRateAndBoundaries() {

        animatedObject.setFrameRate(-1);
        animatedObject.setLowerAnimationIndex(-1);
        animatedObject.setUpperAnimationIndex(-1);
        assertThrows(
                AnimatedObject.PreconditionNotMetForAnimatingObjectException.class,
                () -> animatedObject.setAnimated(true)
        );
    }

    @Test
    @DisplayName("Given correct context, setting game object to true shall work.")
    public void givenCorrectContextSettingVisibleToTrueWorks() {

        animatedObject.setFrameRate(50);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(2);
        animatedObject.setAnimated(true);
        assertThat(animatedObject.isAnimated(), is(true));
    }

    @Test
    @DisplayName("Has image index property.")
    public void hasImageIndexProperty() {

        assertThat(animatedObject, hasProperty("imageIndex"));
    }

    @Test
    @DisplayName("Image index property is of type integer.")
    public void imageIndexPropertyOfTypeInteger() {

        assertThat(animatedObject.getImageIndex(), isA(Integer.class));
    }

    @Test
    @DisplayName("Setting lower animation index property also determines image index.")
    public void lowerAnimationIndexAlsoSetImageIndex() {

        animatedObject.setLowerAnimationIndex(-1);
        animatedObject.setImageIndex(-1);
        animatedObject.setLowerAnimationIndex(0);

        assertThat(animatedObject.getImageIndex(), is(0));
    }

    @Test
    @DisplayName("Animated object is a time listener.")
    public void animatedObjectIsATimeListener() {

        assertThat(animatedObject, isA(TimeListener.class));
    }

    @Test
    @DisplayName("When not being animated current image index does not change.")
    public void whenNotBeingAnimatedCurrentImageIndexDoesNotChange() {

        animatedObject.setFrameRate(4);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(3);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedObject.setAnimated(false);
        gameWorld.pulse(300);

        assertThat(animatedObject.getImageIndex(), is(0));
    }

    @Test
    @DisplayName("When being animated current image index reflects progressed time and frame rate.")
    public void whenBeingAnimatedCurrentImageIndexReflectsProgressedTimeAndFrameRate() {

        animatedObject.setFrameRate(4);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(3);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedObject.setAnimated(true);
        gameWorld.pulse(300);

        assertThat(animatedObject.getImageIndex(), is(1));
    }

    @Test
    @DisplayName("Given frame rate of 4 per second, setting pulse to 500 ms should switch to frame 3 (index 2).")
    public void testFrameRateCalculationPartOne() {

        animatedObject.setFrameRate(4);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(3);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedObject.setAnimated(true);
        gameWorld.pulse(500);

        assertThat(animatedObject.getImageIndex(), is(2));
    }

    @Test
    @DisplayName("Given frame rate of 4 per second and 2 frames, setting pulse to 500 ms should switch to frame 0 (index 0).")
    public void testFrameRateCalculationPartTwo() {

        animatedObject.setFrameRate(4);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(1);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedObject.setAnimated(true);
        gameWorld.pulse(500);

        assertThat(animatedObject.getImageIndex(), is(0));
    }

    @Test
    @DisplayName("Given frame rate of 50 and 7 frames, setting pulse to 900 ms should switch to frame 4 (index 3).")
    public void testFrameRateCalculationPartThree() {

        animatedObject.setFrameRate(50);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(6);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedObject.setAnimated(true);
        gameWorld.pulse(900);

        assertThat(animatedObject.getImageIndex(), is(3));
    }

    @Test
    @DisplayName("PropertyChangeListener can be added and are supported")
    public void propChangeListenerCanBeAddedAndAreSupported() {

        @SuppressWarnings("Convert2Lambda")
        final PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            @SneakyThrows
            public void propertyChange(PropertyChangeEvent evt) {
                throw new Exception(evt.getPropertyName());
            }
        };

        Exception exception;

        animatedObject.addPropertyChangeListener(listener);

        exception = assertThrows(
                Exception.class,
                () -> animatedObject.setFrameRate(50)
        );
        assertThat(exception.getMessage(), is("frameRate"));

        exception = assertThrows(
                Exception.class,
                () -> animatedObject.setLowerAnimationIndex(0)
        );
        assertThat(exception.getMessage(), is("lowerAnimationIndex"));

        exception = assertThrows(
                Exception.class,
                () -> animatedObject.setUpperAnimationIndex(6)
        );
        assertThat(exception.getMessage(), is("upperAnimationIndex"));

        exception = assertThrows(
                Exception.class,
                () -> animatedObject.setAnimated(true)
        );
        assertThat(exception.getMessage(), is("animated"));

        exception = assertThrows(
                Exception.class,
                () -> animatedObject.setImageIndex(2)
        );
        assertThat(exception.getMessage(), is("imageIndex"));
    }
}
