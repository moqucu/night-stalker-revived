package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameWorldTest {

    private final GameWorld gameWorld = new GameWorld();

    @Test
    public void hasTimeProperty() {

        assertThat(gameWorld, hasProperty("time"));
    }

    @Test
    public void timePropertyOfTypeTime() {

        assertThat(gameWorld.getTime(), isA(Long.class));
    }

    @Test
    public void hasMazeGrahProperty() {

        assertThat(gameWorld, hasProperty("mazeGraph"));
    }

    @Test
    public void mazeGraphPropertyOfTypeTime() {

        assertThat(gameWorld.getMazeGraph(), isA(MazeGraphV2.class));
    }

    @Test
    public void aPulseOfThousandMillisecondsWillProgressTimeAccordingly() {

        long originalTime = gameWorld.getTime();
        gameWorld.pulse(1000);
        assertThat(gameWorld.getTime(), is(originalTime + 1000));
    }

    @Test
    public void hasObjectsProperty() {

        assertThat(gameWorld, hasProperty("objects"));
    }

    @Test
    public void objectsPropertyOfTypeMap() {

        assertThat(gameWorld.getObjects(), isA(Map.class));
    }

    @Test
    public void hasTimeListenersProperty() {

        assertThat(gameWorld, hasProperty("timeListeners"));
    }

    @Test
    public void timeListenersPropertyOfTypeSet() {

        assertThat(gameWorld.getTimeListeners(), isA(Set.class));
    }


    @Test
    public void afterAddingAGameObjectItCanBeAccessedByItsId() {

        final GameObject genericGameObject = new GameObject() {
        };
        gameWorld.add(genericGameObject);
        assertThat(gameWorld.getObjects().containsKey(genericGameObject.getId()), is(true));
    }

    @Test
    public void afterSomeTimePassesAddedObjectMovesWithExpectedVelocity() {

        final MovableObject gameObject = new MovableObject() {
        };
        gameObject.setMazeGraphFileName("MazeGraphTest.json");
        gameObject.setMazeAlgorithm(MazeAlgorithm.OuterRing);
        gameObject.getAbsolutePosition().setX(32.0);
        gameObject.getAbsolutePosition().setY(0.0);
        gameObject.setVelocity(20);
        gameWorld.add(gameObject);

        gameObject.setDirection(Direction.Down);
        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(24.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(1000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(4.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(32.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(36.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(1000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(32.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(56.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(4000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(72.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(4000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(40.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getAbsolutePosition().getX(), is(32.0));
        assertThat(gameObject.getAbsolutePosition().getY(), is(0.0));
        gameObject.setInMotion(false);
    }
}
