package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.moqucu.games.nightstalker.view.background.BunkerSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class BunkerSpriteTest {

    private final BunkerSprite bunker = new BunkerSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(bunker, isA(SpriteV2.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(bunker.getModel().getImageMapFileName(), is("/images/bunker.png"));
    }
}
