package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DisplayableSpriteTest {

    private final DisplayableSprite displayableSprite = new DisplayableSprite() {
    };

    @Test
    public void hasModelProperty() {

        assertThat(displayableSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeGameObject() {

        assertThat(displayableSprite.getModel(), isA(DisplayableObject.class));
    }

    @Test
    public void spriteInstanceOfImageView() {

        assertThat(displayableSprite, isA(ImageView.class));
    }

    @Test
    public void settingImageMapFileNamePropertyCreatesImage() {

        final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("part_1_a.gif")));
        displayableSprite.getModel().setImageMapFileName("part_1_a.gif");
        assertThat(displayableSprite.getImage().getUrl(), is(image.getUrl()));
    }

    @Test
    @SneakyThrows
    public void changeOfInitialIndexWillAdjustViewportAccordingly() {

        final int index = 4;
        final int horizontalIndex = index % 20;
        final int verticalIndex = index / 20;
        displayableSprite.getModel().setInitialImageIndex(index);
        final Rectangle2D viewport = displayableSprite.getViewport();
        assertThat(viewport.getMinX(), is(horizontalIndex * displayableSprite.getModel().getWidth()));
        assertThat(viewport.getMinY(), is(verticalIndex * displayableSprite.getModel().getHeight()));
        assertThat(viewport.getWidth(), is(displayableSprite.getModel().getWidth()));
        assertThat(viewport.getHeight(), is(displayableSprite.getModel().getHeight()));
    }

    @Test
    public void javaFxNodeIdEqualsModelObjectId() {

        assertThat(displayableSprite.getId(), is(displayableSprite.getModel().getObjectId()));
    }

    @Test
    public void changingVisiblePropertyWorksOnTwo() {

        displayableSprite.getModel().setObjectVisible(true);
        assertThat(displayableSprite.isVisible(), is(displayableSprite.getModel().isObjectVisible()));
        displayableSprite.setVisible(false);
        assertThat(displayableSprite.isVisible(), is(displayableSprite.getModel().isObjectVisible()));
    }

    @Test
    public void changingXPositionChangesSpriteXCoordinate() {

        displayableSprite.getModel().setXPosition(50);
        assertThat(displayableSprite.getX(), is(displayableSprite.getModel().getXPosition()));

        displayableSprite.setX(75);
        assertThat(displayableSprite.getX(), is(displayableSprite.getModel().getXPosition()));
    }

    @Test
    public void changingYPositionChangesSpriteYCoordinate() {

        displayableSprite.getModel().setYPosition(150);
        assertThat(displayableSprite.getY(), is(displayableSprite.getModel().getYPosition()));

        displayableSprite.setY(275);
        assertThat(displayableSprite.getY(), is(displayableSprite.getModel().getYPosition()));
    }

    @Test
    public void hasInitialImageIndexProperty() {

        assertThat(displayableSprite, hasProperty("initialImageIndex"));
    }

    @Test
    public void initialIndexImagePropertyNotNull() {

        assertThat(displayableSprite.initialImageIndexProperty(), is(notNullValue()));
    }

    @Test
    public void initialIndexImagePropertyIsBound() {

        displayableSprite.setInitialImageIndex(5);
        assertThat(displayableSprite.getModel().getInitialImageIndex(), is(5));

        displayableSprite.getModel().setInitialImageIndex(7);
        assertThat(displayableSprite.getInitialImageIndex(), is(7));
    }

    @Test
    public void settingNewModelRemovesAnyOldPropertyBindings() {

        final DisplayableObject displayableObject = displayableSprite.getModel();
        displayableObject.setImageMapFileName("test/bat.png");
        displayableObject.setInitialImageIndex(2);
        displayableObject.setObjectVisible(false);

        assertThat(displayableSprite.isVisible(), is(false));
        displayableObject.setObjectVisible(true);
        assertThat(displayableSprite.isVisible(), is(true));

        final DisplayableObject newDisplayableObject = new DisplayableObject() {
        };
        newDisplayableObject.setImageMapFileName("bat.png");
        newDisplayableObject.setInitialImageIndex(2);
        newDisplayableObject.setObjectVisible(false);
        displayableSprite.setModel(newDisplayableObject);

        displayableSprite.setVisible(false);
        assertThat(displayableObject.isObjectVisible(), is(true));
        assertThat(displayableSprite.getModel().isObjectVisible(), is(false));
    }
}
