package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.moqucu.games.nightstalker.view.background.HallwaySprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class HallwaySpriteTest {

    private final HallwaySprite hallway = new HallwaySprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(hallway, isA(SpriteV2.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(hallway.getModel().getImageMapFileName(), is("/images/hallway.png"));
    }
}
