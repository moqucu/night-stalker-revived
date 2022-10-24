package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.background.BunkerSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class BunkerSpriteTest {

    private final BunkerSprite bunker = new BunkerSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(bunker, isA(Sprite.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(bunker.getModel().getImageMapFileName(), is("/images/bunker.png"));
    }
}
