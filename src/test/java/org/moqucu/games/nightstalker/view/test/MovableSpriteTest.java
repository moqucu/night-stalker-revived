package org.moqucu.games.nightstalker.view.test;

import javafx.beans.property.adapter.JavaBeanObjectProperty;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.MovableSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MovableSpriteTest {

    private final MovableSprite movableSprite = new MovableSprite(){
    };

    @Test
    public void modelIsOfTypeGameObject() {

        assertThat(movableSprite.getModel(), isA(MovableObject.class));
    }

    @Test
    public void spriteIsOfTypeAnimatedSprite() {

        assertThat(movableSprite, isA(AnimatedSprite.class));
    }

    @Test
    public void ensureThatMotionWillMoveTheXAndYCoordinatesAccordingly() {

        final MovableObject movableObject = new MovableObject() {
        };
        movableObject.setImageMapFileName("bat.png");
        movableObject.setInitialImageIndex(1);
        movableObject.setObjectVisible(true);
        movableObject.setFrameRate(10);
        movableObject.setLowerAnimationIndex(0);
        movableObject.setUpperAnimationIndex(9);
        movableObject.setAnimated(true);
        movableObject.setDirection(Direction.Left);
        movableObject.setXPosition(96);
        movableObject.setYPosition(32);
        movableObject.setVelocity(32);
        movableObject.setMazeGraphFileName("MazeGraphTest.json");
        movableObject.setMazeAlgorithm(MazeAlgorithm.OuterRing);
        movableObject.setInMotion(true);

        final GameWorld gameWorld = new GameWorld();
        gameWorld.add(movableObject);
        movableSprite.setModel(movableObject);

        assertThat(movableSprite.getX(), is(96.0));
        assertThat(movableSprite.getY(), is(32.0));

        gameWorld.pulse(1000);
        assertThat(movableSprite.getX(), is(64.0));
        assertThat(movableSprite.getY(), is(32.0));

        gameWorld.pulse(1000);
        assertThat(movableSprite.getX(), is(32.0));
        assertThat(movableSprite.getY(), is(32.0));

        movableSprite.getModel().setInMotion(false);
        gameWorld.pulse(1000);
        assertThat(movableSprite.getX(), is(32.0));
        assertThat(movableSprite.getY(), is(32.0));
    }

    @Test
    public void hasInitialDirectionProperty() {

        assertThat(movableSprite, hasProperty("direction"));
    }

    @Test
    public void directionOfTypeDirection() {

        assertThat(movableSprite.getDirection(), isA(Direction.class));
    }

    @Test
    public void directionPropertyOfTypeDirectionProperty() {

        assertThat(movableSprite.directionProperty(), isA(JavaBeanObjectProperty.class));
    }
}
