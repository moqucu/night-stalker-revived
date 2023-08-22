package org.moqucu.games.nightstalker.view.hero.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.hero.NightStalker;
import org.moqucu.games.nightstalker.view.MovableSprite;
import org.moqucu.games.nightstalker.view.hero.NightStalkerSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NightStalkerSpriteTest {

    private final NightStalkerSprite nightStalkerSprite = new NightStalkerSprite();

    @Test
    public void nightStalkerIsOfTypeSprite() {

        assertThat(nightStalkerSprite, isA(MovableSprite.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(nightStalkerSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeBatModel() {

        assertThat(nightStalkerSprite.getModel(), isA(NightStalker.class));
    }

    @Test
    public void settingAModelWorks() {

        final NightStalker nightStalkerModel = new NightStalker();
        nightStalkerSprite.setModel(nightStalkerModel);

        assertThat(nightStalkerSprite.getModel(), is(nightStalkerModel));
    }
}
