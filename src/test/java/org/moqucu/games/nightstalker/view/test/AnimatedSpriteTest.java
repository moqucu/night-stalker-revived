package org.moqucu.games.nightstalker.view.test;

import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class AnimatedSpriteTest {

    private final AnimatedSprite animatedSprite = new AnimatedSprite() {
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

        assertThat(animatedSprite, isA(DisplayableSprite.class));
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
        assertThatViewportFollowsFrameIndex(0);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(1);

        gameWorld.pulse(200);
        assertThatViewportFollowsFrameIndex(3);

        gameWorld.pulse(400);
        assertThatViewportFollowsFrameIndex(7);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(8);

        gameWorld.pulse(100);
        assertThatViewportFollowsFrameIndex(9);
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
        assertThatViewportFollowsFrameIndex(0);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(1);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(3);

        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        gameWorld.pulse(50);
        assertThatViewportFollowsFrameIndex(7);

        gameWorld.pulse(25);
        gameWorld.pulse(25);
        gameWorld.pulse(25);
        gameWorld.pulse(25);
        assertThatViewportFollowsFrameIndex(8);

        gameWorld.pulse(75);
        gameWorld.pulse(25);
        assertThatViewportFollowsFrameIndex(9);

        gameWorld.pulse(75);
        gameWorld.pulse(25);
        assertThatViewportFollowsFrameIndex(0);
    }
}
