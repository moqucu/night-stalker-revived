package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameObjectTest {

    private final GameObject genericGameObject = new GameObject() {
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
    public void hasX() {

        assertThat(genericGameObject, hasProperty("x"));
    }

    @Test
    public void hasY() {

        assertThat(genericGameObject, hasProperty("y"));
    }

    @Test
    public void hasIdProperty() {

        assertThat(genericGameObject, hasProperty("id"));
    }

    @Test
    public void idPropertyOfTypeUUID() {

        assertThat(genericGameObject.getId(), isA(UUID.class));
    }

    @Test
    public void hasWidthProperty() {

        assertThat(genericGameObject, hasProperty("width"));
    }

    @Test
    public void widthIsThirtyTwoPixelsWide() {

        assertThat(genericGameObject.getWidth(), is(32.0));
    }

    @Test
    public void hasHeightProperty() {

        assertThat(genericGameObject, hasProperty("height"));
    }

    @Test
    public void heightIsThirtyTwoPixelsHigh() {

        assertThat(genericGameObject.getHeight(), is(32.0));
    }

    @Test
    public void hasImageMapFileNameProperty() {

        assertThat(genericGameObject, hasProperty("imageMapFileName"));
    }

    @Test
    public void imageMapFileNamePropertyOfTypeString() {

        assertThat(genericGameObject.getImageMapFileName(), instanceOf(String.class));
    }

    @Test
    public void hasInitialImageIndexProperty() {

        assertThat(genericGameObject, hasProperty("initialImageIndex"));
    }

    @Test
    public void initialImageIndexPropertyOfTypeInteger() {

        assertThat(genericGameObject.getInitialImageIndex(), instanceOf(Integer.class));
    }

    @Test
    public void hasVisibleProperty() {

        assertThat(genericGameObject, hasProperty("visible"));
    }

    @Test
    public void visiblePropertyOfTypeBoolean() {

        assertThat(genericGameObject.isVisible(), instanceOf(Boolean.class));
    }

    @Test
    @DisplayName("Cannot set game object visible without correctly set image map and initial index.")
    public void settingVisibleToTrueOnlyPossibleWithRightConditions() {

        genericGameObject.setImageMapFileName("");
        genericGameObject.setInitialImageIndex(-1);
        assertThrows(
                GameObject.PreconditionNotMetForMakingObjectVisibleException.class,
                () -> genericGameObject.setVisible(true)
        );
    }

    @Test
    @DisplayName("Given correct context, setting game object to true shall work.")
    public void givenCorrectContextSettingVisibleToTrueWorks() {

        genericGameObject.setImageMapFileName("test_image.gif");
        genericGameObject.setInitialImageIndex(0);
        genericGameObject.setVisible(true);
        assertThat(genericGameObject.isVisible(), is(true));
    }

    @Test
    @DisplayName("PropertyChangeListener can be added and are supported")
    public void propChangeListenerCanBeAddedAndAreSupported() {

        genericGameObject.addPropertyChangeListener(listener);

        exception = assertThrows(
                Exception.class,
                () -> genericGameObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is("imageMapFileName"));

        exception = assertThrows(
                Exception.class,
                () -> genericGameObject.setInitialImageIndex(0)
        );
        assertThat(exception.getMessage(), is("initialImageIndex"));

        exception = assertThrows(
                Exception.class,
                () -> genericGameObject.setVisible(true)
        );
        assertThat(exception.getMessage(), is("visible"));
    }

    @Test
    @DisplayName("PropertyChangeListener can be removed after having been added")
    public void propChangeListenerCanBeRemovedAfterHavingBeenAdded() {

        genericGameObject.addPropertyChangeListener(listener);
        exception = assertThrows(
                Exception.class,
                () -> genericGameObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is("imageMapFileName"));
        genericGameObject.removePropertyChangeListener(listener);
        genericGameObject.setImageMapFileName("part_1_a.gif");
    }
}
