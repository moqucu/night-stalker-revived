package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.view.AnimatedSpriteV2;
import org.moqucu.games.nightstalker.view.SpriteV2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class AnimatedSpriteV2Test {

    private final AnimatedSpriteV2 animatedSprite = new AnimatedSpriteV2() {
    };

    @Test
    public void hasModelProperty() {

        assertThat(animatedSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeGameObject() {

        assertThat(animatedSprite.getModel(), isA(AnimatedObject.class));
    }

    @Test
    public void modelIsOfTypeSpriteV2() {

        assertThat(animatedSprite, isA(SpriteV2.class));
    }

    private void assertThatViewportFollowsFrameIndex(int frameIndex) {

        final int horizontalIndex = frameIndex % 20;
        final int verticalIndex = frameIndex / 20;
        final Rectangle2D viewport = animatedSprite.getViewport();

        assertThat(viewport.getMinX(), is(horizontalIndex * animatedSprite.getModel().getWidth()));
        assertThat(viewport.getMinY(), is(verticalIndex * animatedSprite.getModel().getHeight()));
        assertThat(viewport.getWidth(), is(animatedSprite.getModel().getWidth()));
        assertThat(viewport.getHeight(), is(animatedSprite.getModel().getHeight()));
    }

    @Test
    public void ensureThatAnimationWillMoveTheViewPortAccordingly() {

        final AnimatedObject animatedObject = new AnimatedObject() {
        };
        animatedObject.setImageMapFileName("bat.png");
        animatedObject.setInitialImageIndex(1);
        animatedObject.setObjectVisible(true);
        animatedObject.setFrameRate(10);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(9);
        animatedObject.setAnimated(true);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedSprite.setModel(animatedObject);
        assertThatViewportFollowsFrameIndex(1);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(2);

        gameWorld.pulse(200);
        assertThatViewportFollowsFrameIndex(4);

        gameWorld.pulse(400);
        assertThatViewportFollowsFrameIndex(8);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(9);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(0);
    }

    @Test
    public void ensureThatAnimationWillMoveTheViewPortAccordinglyWithHalfIntervals() {

        final AnimatedObject animatedObject = new AnimatedObject() {
        };
        animatedObject.setImageMapFileName("bat.png");
        animatedObject.setInitialImageIndex(1);
        animatedObject.setObjectVisible(true);
        animatedObject.setFrameRate(10);
        animatedObject.setLowerAnimationIndex(0);
        animatedObject.setUpperAnimationIndex(9);
        animatedObject.setAnimated(true);
        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(animatedObject);
        animatedSprite.setModel(animatedObject);
        assertThatViewportFollowsFrameIndex(1);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(2);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(4);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(8);

        gameWorld.pulse(25);
        gameWorld.pulse(25);
        gameWorld.pulse(25);
        gameWorld.pulse(25);
        assertThatViewportFollowsFrameIndex(9);

        gameWorld.pulse(75);
        gameWorld.pulse(25);
        assertThatViewportFollowsFrameIndex(0);
    }
}
