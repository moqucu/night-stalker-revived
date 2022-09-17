package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

@ExtendWith(ApplicationExtension.class)
public class SpriteV2Test {

    private final SpriteV2 sprite = new SpriteV2() {
    };

    @Start
    private void start(Stage stage) {

         Pane pane = new Pane();
        pane.setStyle("-fx-background-color: blue;");
        SpriteV2 spriteV2 = new SpriteV2() {
        };
        spriteV2.setImageMapFileName("part_1_a.gif");
        pane.getChildren().add(spriteV2);
        spriteV2.setVisible(true);
        spriteV2.setInitialImageIndex(0);
        spriteV2.setX(40);
        spriteV2.setY(40);

        stage.setScene(new Scene(pane, 800, 600));
        stage.show();
    }

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
    public void settingImageMapFileNamePropertyCreatesImage() {

        final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("part_1_a.gif")));
        sprite.setImageMapFileName("part_1_a.gif");
        assertThat(sprite.getImage().getUrl(), is(image.getUrl()));
    }

    @Test
    @SneakyThrows
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

        Thread.sleep(2000);
    }

    @Test
    public void javaFxNodeIdEqualsModelObjectId() {

        assertThat(sprite.getId(), is(sprite.getModel().getObjectId()));
    }

    @Test
    public void changingVisiblePropertyWorksOnTwo() {

        sprite.setObjectVisible(true);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));
        sprite.setVisible(false);
        assertThat(sprite.isVisible(), is(sprite.getModel().isObjectVisible()));
    }

    @Test
    public void changingXPositionChangesSpriteXCoordinate() {

        sprite.setXPosition(50);
        assertThat(sprite.getX(), is(sprite.getModel().getXPosition()));

        sprite.setX(75);
        assertThat(sprite.getX(), is(sprite.getModel().getXPosition()));
    }

    @Test
    public void changingYPositionChangesSpriteYCoordinate() {

        sprite.setYPosition(150);
        assertThat(sprite.getY(), is(sprite.getModel().getYPosition()));

        sprite.setY(275);
        assertThat(sprite.getY(), is(sprite.getModel().getYPosition()));
    }
}
