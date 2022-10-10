package org.moqucu.games.nightstalker.view.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.enemy.Spider;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;
import org.moqucu.games.nightstalker.view.enemy.SpiderSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpiderSpriteTest {

    private final SpiderSprite spiderSprite = new SpiderSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(spiderSprite, isA(MovableSpriteV2.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(spiderSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeBatModel() {

        assertThat(spiderSprite.getModel(), isA(Spider.class));
    }

     @Test
    public void settingAModelWorks() {

        final Spider spiderModel = new Spider();
        spiderSprite.setModel(spiderModel);

        assertThat(spiderSprite.getModel(), is(spiderModel));
    }
}
