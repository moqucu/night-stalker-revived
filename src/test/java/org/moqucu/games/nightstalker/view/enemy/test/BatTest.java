package org.moqucu.games.nightstalker.view.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.enemy.BatModel;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;
import org.moqucu.games.nightstalker.view.enemy.Bat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BatTest {

    private final Bat bat = new Bat();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(bat, isA(MovableSpriteV2.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(bat, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeBatModel() {

        assertThat(bat.getModel(), isA(BatModel.class));
    }

    @Test
    public void settingAModelWorks() {

        final BatModel batModel = new BatModel();
        bat.setModel(batModel);

        assertThat(bat.getModel(), is(batModel));
    }
}
