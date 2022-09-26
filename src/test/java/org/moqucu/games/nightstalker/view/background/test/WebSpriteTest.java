package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.moqucu.games.nightstalker.view.background.WebSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class WebSpriteTest {

    private final WebSprite web = new WebSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(web, isA(SpriteV2.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat(web.getModel().getImageMapFileName(), is("/images/web.png"));
    }
}
