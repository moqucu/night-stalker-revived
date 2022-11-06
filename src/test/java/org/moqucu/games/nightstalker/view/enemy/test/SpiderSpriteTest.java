package org.moqucu.games.nightstalker.view.enemy.test;

import javafx.beans.property.BooleanProperty;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.enemy.Spider;
import org.moqucu.games.nightstalker.view.MovableSprite;
import org.moqucu.games.nightstalker.view.enemy.SpiderSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpiderSpriteTest {

    private final SpiderSprite spiderSprite = new SpiderSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(spiderSprite, isA(MovableSprite.class));
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

    @Test
    public void hasInMotionProperty() {

        assertThat(spiderSprite, hasProperty("inMotion"));
    }

    @Test
    public void inMotionOfTypeBoolean() {

        assertThat(spiderSprite.isInMotion(), isA(Boolean.class));
    }

    @Test
    public void inMotionPropertyOfTypeBooleanProperty() {

        assertThat(spiderSprite.inMotion(), isA(BooleanProperty.class));
    }

    @Test
    public void testInMotionPropertyBinding() {

        assertThat(spiderSprite.isInMotion(), is(false));
        assertThat(spiderSprite.inMotion().getBean(), is(spiderSprite.getModel()));
        assertThat(spiderSprite.inMotion().getName(), is("inMotion"));
        assertThrows(
                Throwable.class,
                () -> spiderSprite.setInMotion(false)
        );
    }

    @Test
    public void hasAnimatedProperty() {

        assertThat(spiderSprite, hasProperty("animated"));
    }

    @Test
    public void animatedOfTypeBoolean() {

        assertThat(spiderSprite.isAnimated(), isA(Boolean.class));
    }

    @Test
    public void animatedPropertyOfTypeBooleanProperty() {

        assertThat(spiderSprite.animated(), isA(BooleanProperty.class));
    }

    @Test
    public void testAnimatedPropertyBinding() {

        assertThat(spiderSprite.isAnimated(), is(false));
        assertThat(spiderSprite.animated().getBean(), is(spiderSprite.getModel()));
        assertThat(spiderSprite.animated().getName(), is("animated"));
        assertThrows(
                Throwable.class,
                () -> spiderSprite.setAnimated(false)
        );
    }
}