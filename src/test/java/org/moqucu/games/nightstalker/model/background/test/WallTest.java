package org.moqucu.games.nightstalker.model.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Collidable;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.model.background.Wall;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WallTest {

    @Test
    public void thereIsAWallClass() {

        assertThat(new Wall(), is(notNullValue()));
    }

    @Test
    public void wallIsOfTypeDisplayableObject() {

        assertThat(new Wall(), isA(DisplayableObject.class));
    }

    @Test
    public void wallIsOfTypeCollidable() {

        assertThat(new Wall(), isA(Collidable.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat((new Wall()).getImageMapFileName(), is("/images/wall.png"));
    }
}
