package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameObjectImpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameObjectTest {

    private final GameObject gameObject = new GameObjectImpl() {
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

        assertThat(gameObject, hasProperty("XPosition"));
    }

    @Test
    public void hasY() {

        assertThat(gameObject, hasProperty("YPosition"));
    }

    @Test
    public void hasIdProperty() {

        assertThat(gameObject, hasProperty("objectId"));
    }

    @Test
    public void idPropertyOfTypeUUID() {

        assertThat(gameObject.getObjectId(), isA(String.class));
    }

    @Test
    public void hasWidthProperty() {

        assertThat(gameObject, hasProperty("width"));
    }

    @Test
    public void widthIsThirtyTwoPixelsWide() {

        assertThat(gameObject.getWidth(), is(32.0));
    }

    @Test
    public void hasHeightProperty() {

        assertThat(gameObject, hasProperty("height"));
    }

    @Test
    public void heightIsThirtyTwoPixelsHigh() {

        assertThat(gameObject.getHeight(), is(32.0));
    }

    @Test
    public void hasImageMapFileNameProperty() {

        assertThat(gameObject, hasProperty("imageMapFileName"));
    }

    @Test
    public void imageMapFileNamePropertyOfTypeString() {

        assertThat(gameObject.getImageMapFileName(), instanceOf(String.class));
    }

    @Test
    public void hasInitialImageIndexProperty() {

        assertThat(gameObject, hasProperty("initialImageIndex"));
    }

    @Test
    public void initialImageIndexPropertyOfTypeInteger() {

        assertThat(gameObject.getInitialImageIndex(), instanceOf(Integer.class));
    }

    @Test
    public void hasVisibleProperty() {

        assertThat(gameObject, hasProperty("objectVisible"));
    }

    @Test
    public void visiblePropertyOfTypeBoolean() {

        assertThat(gameObject.isObjectVisible(), instanceOf(Boolean.class));
    }

    @Test
    @DisplayName("Cannot set game object visible without correctly set image map and initial index.")
    public void settingVisibleToTrueOnlyPossibleWithRightConditions() {

        gameObject.setImageMapFileName("");
        gameObject.setInitialImageIndex(-1);
        assertThrows(
                GameObjectImpl.PreconditionNotMetForMakingObjectVisibleException.class,
                () -> gameObject.setObjectVisible(true)
        );
    }

    @Test
    @DisplayName("Given correct context, setting game object to true shall work.")
    public void givenCorrectContextSettingVisibleToTrueWorks() {

        gameObject.setImageMapFileName("test_image.gif");
        gameObject.setInitialImageIndex(0);
        gameObject.setObjectVisible(true);
        assertThat(gameObject.isObjectVisible(), is(true));
    }

    @Test
    @DisplayName("PropertyChangeListener can be added and are supported")
    public void propChangeListenerCanBeAddedAndAreSupported() {

        gameObject.addPropertyChangeListener(listener);

        exception = assertThrows(
                Exception.class,
                () -> gameObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is("imageMapFileName"));

        exception = assertThrows(
                Exception.class,
                () -> gameObject.setInitialImageIndex(0)
        );
        assertThat(exception.getMessage(), is("initialImageIndex"));

        exception = assertThrows(
                Exception.class,
                () -> gameObject.setObjectVisible(true)
        );
        assertThat(exception.getMessage(), is("objectVisible"));
    }

    @Test
    @DisplayName("PropertyChangeListener can be removed after having been added")
    public void propChangeListenerCanBeRemovedAfterHavingBeenAdded() {

        gameObject.addPropertyChangeListener(listener);
        exception = assertThrows(
                Exception.class,
                () -> gameObject.setImageMapFileName("part_1_a.gif")
        );
        assertThat(exception.getMessage(), is("imageMapFileName"));
        gameObject.removePropertyChangeListener(listener);
        gameObject.setImageMapFileName("part_1_a.gif");
    }
}
