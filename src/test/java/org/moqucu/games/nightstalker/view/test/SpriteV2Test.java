package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.view.SpriteV2;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

public class SpriteV2Test {

    private final SpriteV2 sprite = new SpriteV2() {
    };

    @Test
    public void hasModelProperty() {

        assertThat(sprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeGameObject() {

        assertThat(sprite.getModel(), isA(GameObject.class));
    }

    @Test
    public void spriteInstanceOfImageView() {

        assertThat(sprite, isA(ImageView.class));
    }

    @Test
    public void settingImageMapFileNamePropertyCreatesImage() {

        final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("part_1_a.gif")));
        sprite.getModel().setImageMapFileName("part_1_a.gif");
        assertThat(sprite.getImage().getUrl(), is(image.getUrl()));
    }

    @Test
    @SneakyThrows
    public void changeOfInitialIndexWillAdjustViewportAccordingly() {

        final int index = 4;
        final int horizontalIndex = index % 20;
        final int verticalIndex = index / 20;
        sprite.getModel().setInitialImageIndex(index);
        final Rectangle2D viewport = sprite.getViewport();
        assertThat(viewport.getMinX(), is(horizontalIndex * sprite.getModel().getWidth()));
        assertThat(viewport.getMinY(), is(verticalIndex * sprite.getModel().getHeight()));
        assertThat(viewport.getWidth(), is(sprite.getModel().getWidth()));
        assertThat(viewport.getHeight(), is(sprite.getModel().getHeight()));
    }

    @Test
    public void javaFxNodeIdEqualsModelObjectId() {

        assertThat(sprite.getId(), is(sprite.getModel().getObjectId()));
    }

    @Test
    public void changingVisiblePropertyWorksOnTwo() {

        sprite.getModel().setObjectVisible(true);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));
        sprite.setVisible(false);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));
    }

    @Test
    public void changingXPositionChangesSpriteXCoordinate() {

        sprite.getModel().setXPosition(50);
        assertThat(sprite.getX(), is(sprite.getModel().getXPosition()));

        sprite.setX(75);
        assertThat(sprite.getX(), is(sprite.getModel().getXPosition()));
    }

    @Test
    public void changingYPositionChangesSpriteYCoordinate() {

        sprite.getModel().setYPosition(150);
        assertThat(sprite.getY(), is(sprite.getModel().getYPosition()));

        sprite.setY(275);
        assertThat(sprite.getY(), is(sprite.getModel().getYPosition()));
    }

    @Test
    public void settingNewModelRemovesAnyOldPropertyBindings() {

        final GameObject gameObject = sprite.getModel();
        gameObject.setImageMapFileName("test/bat.png");
        gameObject.setInitialImageIndex(2);
        gameObject.setObjectVisible(false);

        assertThat(sprite.isVisible(), is(false));
        gameObject.setObjectVisible(true);
        assertThat(sprite.isVisible(), is(true));

        final GameObject newGameObject = new GameObject() {
        };
        newGameObject.setImageMapFileName("bat.png");
        newGameObject.setInitialImageIndex(2);
        newGameObject.setObjectVisible(false);
        sprite.setModel(newGameObject);

        sprite.setVisible(false);
        assertThat(gameObject.isObjectVisible(), is(true));
        assertThat(sprite.getModel().isObjectVisible(), is(false));
    }
}
