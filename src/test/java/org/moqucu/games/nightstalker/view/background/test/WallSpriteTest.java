package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.moqucu.games.nightstalker.view.background.WallSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WallSpriteTest {

    private final WallSprite wall = new WallSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(wall, isA(SpriteV2.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(wall.getModel().getImageMapFileName(), is("/images/wall.png"));
    }
}
