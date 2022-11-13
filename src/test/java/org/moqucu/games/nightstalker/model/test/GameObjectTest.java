package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;

public class GameObjectTest {

    private final GameObject gameObject = new GameObject();

    @Test
    public void hasIdProperty() {

        assertThat(gameObject, hasProperty("objectId"));
    }

    @Test
    public void idPropertyOfTypeUUID() {

        assertThat(gameObject.getObjectId(), isA(String.class));
    }
}
