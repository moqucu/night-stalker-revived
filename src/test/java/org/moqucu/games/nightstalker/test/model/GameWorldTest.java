package org.moqucu.games.nightstalker.test.model;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MovableGameObject;

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

        final MovableGameObject gameObject = new MovableGameObject() {
        };
        gameObject.getPosition().setX(0.0);
        gameObject.getPosition().setY(0.0);
        gameObject.setVelocity(20);
        gameWorld.add(gameObject);

        gameObject.setDirection(Direction.Down);
        gameObject.setInMotion();
        gameWorld.pulse(2000);
        assertThat(gameObject.getPosition().getX(), is(0.0));
        assertThat(gameObject.getPosition().getY(), is(40.0));
        gameObject.stop();

        gameObject.setDirection(Direction.Up);
        gameObject.setInMotion();
        gameWorld.pulse(1000);
        assertThat(gameObject.getPosition().getX(), is(0.0));
        assertThat(gameObject.getPosition().getY(), is(20.0));
        gameObject.stop();

        gameObject.setDirection(Direction.Right);
        gameObject.setInMotion();
        gameWorld.pulse(2000);
        assertThat(gameObject.getPosition().getX(), is(40.0));
        assertThat(gameObject.getPosition().getY(), is(20.0));
        gameObject.stop();

        gameObject.setDirection(Direction.Left);
        gameObject.setInMotion();
        gameWorld.pulse(1000);
        assertThat(gameObject.getPosition().getX(), is(20.0));
        assertThat(gameObject.getPosition().getY(), is(20.0));
        gameObject.stop();
    }
}
