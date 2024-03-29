package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GameWorldTest {

    private final GameWorld gameWorld = new GameWorld();

    @Test
    public void hasTimeProperty() {

        assertThat(gameWorld, hasProperty("time"));
    }

    @Test
    public void timePropertyOfTypeDouble() {

        assertThat(gameWorld.getTime(), isA(Double.class));
    }

    @Test
    public void hasMazeGrahProperty() {

        assertThat(gameWorld, hasProperty("mazeGraph"));
    }

    @Test
    public void mazeGraphPropertyOfTypeTime() {

        assertThat(gameWorld.getMazeGraph(), isA(MazeGraph.class));
    }

    @Test
    public void aPulseOfThousandMillisecondsWillProgressTimeAccordingly() {

        final double originalTime = gameWorld.getTime();
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

        final DisplayableObject genericGameObject = new DisplayableObject() {
        };
        gameWorld.add(genericGameObject);
        assertThat(gameWorld.getObjects().containsKey(genericGameObject.getObjectId()), is(true));
    }

    @Test
    public void afterSomeTimePassesAddedObjectMovesWithExpectedVelocity() {

        final MovableObject gameObject = new MovableObject() {
        };
        gameObject.setMazeGraphFileName("MazeGraphTest.json");
        gameObject.setMazeAlgorithm(MazeAlgorithm.OuterRing);
        gameObject.setXPosition(32.0);
        gameObject.setYPosition(0.0);
        gameObject.setVelocity(20);
        gameWorld.add(gameObject);

        gameObject.setDirection(Direction.Down);
        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getXPosition(), is(24.0));
        assertThat(gameObject.getYPosition(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(1000);
        assertThat(gameObject.getXPosition(), is(4.0));
        assertThat(gameObject.getYPosition(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getXPosition(), is(32.0));
        assertThat(gameObject.getYPosition(), is(36.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(1000);
        assertThat(gameObject.getXPosition(), is(32.0));
        assertThat(gameObject.getYPosition(), is(56.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(4000);
        assertThat(gameObject.getXPosition(), is(32.0));
        assertThat(gameObject.getYPosition(), is(56.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(4000);
        assertThat(gameObject.getXPosition(), is(88.0));
        assertThat(gameObject.getYPosition(), is(32.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(2000);
        assertThat(gameObject.getXPosition(), is(96.0));
        assertThat(gameObject.getYPosition(), is(0.0));
        gameObject.setInMotion(false);

        gameObject.setInMotion(true);
        gameWorld.pulse(5000);
        assertThat(gameObject.getXPosition(), is(32.0));
        assertThat(gameObject.getYPosition(), is(28.0));
        gameObject.setInMotion(false);
    }

    @Test
    public void aGameWorldIsResettable() {

        assertThat(gameWorld, isA(Resettable.class));
    }

    @Test
    public void whenAddingResettableObjectsResetIsOperatingOnThem() {

        final String expectedThrowableMessage = "reset()!";

        final GameWorld aGameWorld = new GameWorld();
        final GreyRobot aGreyRobot = Mockito.mock(GreyRobot.class);
        aGameWorld.add(aGreyRobot);

        Mockito.doThrow(new RuntimeException(expectedThrowableMessage)).when(aGreyRobot).reset();

         Throwable throwable = assertThrows(RuntimeException.class, aGameWorld::reset);
         assertThat(throwable.getMessage(), is(expectedThrowableMessage));
    }
}
