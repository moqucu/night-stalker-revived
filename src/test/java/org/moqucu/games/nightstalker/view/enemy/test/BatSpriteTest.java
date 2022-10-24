package org.moqucu.games.nightstalker.view.enemy.test;

import javafx.beans.property.DoubleProperty;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.enemy.Bat;
import org.moqucu.games.nightstalker.view.MovableSprite;
import org.moqucu.games.nightstalker.view.enemy.BatSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BatSpriteTest {

    private final BatSprite batSprite = new BatSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(batSprite, isA(MovableSprite.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(batSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeBatModel() {

        assertThat(batSprite.getModel(), isA(Bat.class));
    }

    @Test
    public void hasSleepTimeProperty() {

        assertThat(batSprite, hasProperty("sleepTimeInMillis"));
    }

    @Test
    public void sleepTimeOfTypeDouble() {

        assertThat(batSprite.getSleepTimeInMillis(), isA(Double.class));
    }

    @Test
    public void sleepTimePropertyOfTypeDoubleProperty() {

        assertThat(batSprite.sleepTimeInMillis(), isA(DoubleProperty.class));
    }

    @Test
    public void settingAModelWorks() {

        final Bat batModel = new Bat();
        batSprite.setModel(batModel);

        assertThat(batSprite.getModel(), is(batModel));
    }

    @Test
    public void setSpriteSleepTimeAlsoModifiesModelSleepTime() {

        final BatSprite anotherBatSprite = new BatSprite();
        anotherBatSprite.setSleepTimeInMillis(6.5);

        assertThat(anotherBatSprite.getModel().getSleepTime(), is(6.5));
    }

    @Test
    public void setModelSleepTimeAlsoModifiesSpriteSleepTime() {

        final BatSprite anotherBatSprite = new BatSprite();
        anotherBatSprite.getModel().setSleepTime(3.5);

        assertThat(anotherBatSprite.getSleepTimeInMillis(), is(3.5));
    }
}
