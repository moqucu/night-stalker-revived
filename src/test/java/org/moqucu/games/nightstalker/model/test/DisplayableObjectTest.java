package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.BoundingBox;
import org.moqucu.games.nightstalker.model.Collidable;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.PropertyNames;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisplayableObjectTest {

    private final DisplayableObject displayableObject = new DisplayableObject() {
    };

    @SuppressWarnings("Convert2Lambda")
    private final PropertyChangeListener listener = new PropertyChangeListener() {
        @Override
        @SneakyThrows
        public void propertyChange(PropertyChangeEvent evt) {
            throw new Exception(evt.getPropertyName());
        }
    };

    private Exception exception;

    @Test
    public void instanceOfGameObject() {

        assertThat(displayableObject, isA(GameObject.class));
    }

    @Test
    public void hasX() {

        assertThat(displayableObject, hasProperty("XPosition"));
    }

    @Test
    public void hasY() {

        assertThat(displayableObject, hasProperty("YPosition"));
    }

    @Test
    public void hasWidthProperty() {

        assertThat(displayableObject, hasProperty("width"));
    }

    @Test
    public void widthIsThirtyTwoPixelsWide() {

        assertThat(displayableObject.getWidth(), is(32.0));
    }

    @Test
    public void hasHeightProperty() {

        assertThat(displayableObject, hasProperty("height"));
    }

    @Test
    public void heightIsThirtyTwoPixelsHigh() {

        assertThat(displayableObject.getHeight(), is(32.0));
    }

    @Test
    public void hasImageMapFileNameProperty() {

        assertThat(displayableObject, hasProperty("imageMapFileName"));
    }

    @Test
    public void imageMapFileNamePropertyOfTypeString() {

        assertThat(displayableObject.getImageMapFileName(), instanceOf(String.class));
    }

    @Test
    public void hasInitialImageIndexProperty() {

        assertThat(displayableObject, hasProperty("initialImageIndex"));
    }

    @Test
    public void initialImageIndexPropertyOfTypeInteger() {

        assertThat(displayableObject.getInitialImageIndex(), instanceOf(Integer.class));
    }

    @Test
    public void hasVisibleProperty() {

        assertThat(displayableObject, hasProperty("objectVisible"));
    }

    @Test
    public void visiblePropertyOfTypeBoolean() {

        assertThat(displayableObject.isObjectVisible(), instanceOf(Boolean.class));
    }

    @Test
    @DisplayName("Cannot set game object visible without correctly set image map and initial index.")
    public void settingVisibleToTrueOnlyPossibleWithRightConditions() {

        displayableObject.setImageMapFileName(null);
        displayableObject.setInitialImageIndex(1);
        assertThrows(
                DisplayableObject.PreconditionNotMetForMakingObjectVisibleException.class,
                () -> displayableObject.setObjectVisible(true)
        );
    }

    @Test
    @DisplayName("Given correct context, setting game object to true shall work.")
    public void givenCorrectContextSettingVisibleToTrueWorks() {

        displayableObject.setImageMapFileName("test_image.gif");
        displayableObject.setInitialImageIndex(0);
        displayableObject.setObjectVisible(true);
        assertThat(displayableObject.isObjectVisible(), is(true));
    }

    @Test
    @DisplayName("Setting initial image below zero or above 239 leads to exception.")
    public void testInitialImageOutOfBoundsException() {

        assertThrows(
                DisplayableObject.InitialImageIndexOutOfBoundsException.class,
                () -> displayableObject.setInitialImageIndex(-1)
        );
        assertThrows(
                DisplayableObject.InitialImageIndexOutOfBoundsException.class,
                () -> displayableObject.setInitialImageIndex(240)
        );
    }

    @Test
    @DisplayName("PropertyChangeListener can be added and are supported")
    public void propChangeListenerCanBeAddedAndAreSupported() {

        displayableObject.addPropertyChangeListener(listener);

        exception = assertThrows(
                Exception.class,
                () -> displayableObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is(PropertyNames.IMAGE_MAP_FILE_NAME));

        exception = assertThrows(
                Exception.class,
                () -> displayableObject.setInitialImageIndex(0)
        );
        assertThat(exception.getMessage(), is(PropertyNames.INITIAL_IMAGE_INDEX));

        exception = assertThrows(
                Exception.class,
                () -> displayableObject.setObjectVisible(true)
        );
        assertThat(exception.getMessage(), is(PropertyNames.OBJECT_VISIBLE));
    }

    @Test
    @DisplayName("PropertyChangeListener can be removed after having been added")
    public void propChangeListenerCanBeRemovedAfterHavingBeenAdded() {

        displayableObject.addPropertyChangeListener(listener);
        exception = assertThrows(
                Exception.class,
                () -> displayableObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is(PropertyNames.IMAGE_MAP_FILE_NAME));
        displayableObject.removePropertyChangeListener(listener);
        displayableObject.setImageMapFileName("part_1_a.gif");
    }

    @Test
    public void implementsCollidableInterface() {

        assertThat(displayableObject, isA(Collidable.class));
    }

    @Test
    public void setBoundingBoxDimensionsAffectsAbsoluteBounds() {

        displayableObject.setXPosition(100);
        displayableObject.setYPosition(200);
        displayableObject.setBoundingBoxDimensions(4, 8, 20, 16);

        final BoundingBox bounds = displayableObject.getAbsoluteBounds();
        assertThat(bounds.getMinX(), is(104.0));
        assertThat(bounds.getMaxX(), is(124.0));
        assertThat(bounds.getMinY(), is(208.0));
        assertThat(bounds.getMaxY(), is(224.0));
    }
}
