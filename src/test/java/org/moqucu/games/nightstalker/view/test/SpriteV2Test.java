package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

@ExtendWith(ApplicationExtension.class)
public class SpriteV2Test {

    private final SpriteV2 sprite = new SpriteV2() {
    };

    @Test
    public void hasModelProperty() {

        assertThat(sprite, hasProperty("model"));
    }

    @Test
    public void spriteInstanceOfGameObject() {

        assertThat(sprite, isA(GameObject.class));
    }

    @Test
    public void spriteInstanceOfImageView() {

        assertThat(sprite, isA(ImageView.class));
    }

    @Test
    public void changeOfInitialIndexWillAdjustViewportAccordingly() {

        final int index = 4;
        final int horizontalIndex = index % 20;
        final int verticalIndex = index / 20;
        sprite.setInitialImageIndex(index);
        final Rectangle2D viewport = sprite.getViewport();
        assertThat(viewport.getMinX(), is(horizontalIndex * sprite.getWidth()));
        assertThat(viewport.getMinY(), is(verticalIndex * sprite.getHeight()));
        assertThat(viewport.getWidth(), is(sprite.getWidth()));
        assertThat(viewport.getHeight(), is(sprite.getHeight()));
    }

    @Test
    public void javaFxNodeIdEqualsModelObjectId() {

        assertThat(sprite.getId(), is(sprite.getModel().getObjectId()));
        sprite.setObjectVisible(true);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));
        sprite.setVisible(false);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));

    }
}
