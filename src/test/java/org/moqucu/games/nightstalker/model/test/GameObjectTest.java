package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameObject;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

public class GameObjectTest {

    private final GameObject genericGameObject = new GameObject() {
    };

    @Test
    public void hasPosition() {

        assertThat(genericGameObject, hasProperty("absolutePosition"));
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
}
