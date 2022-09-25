package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.moqucu.games.nightstalker.view.background.WallTypeOne;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class WallTypeOneTest {

    private final WallTypeOne wall = new WallTypeOne();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(wall, isA(SpriteV2.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(wall.getModel().getImageMapFileName(), is("../images/wall.png"));
    }

    @Test
    public void initialImageIndexIsCorrect() {

        assertThat(wall.getModel().getInitialImageIndex(), is(0));
    }
}
