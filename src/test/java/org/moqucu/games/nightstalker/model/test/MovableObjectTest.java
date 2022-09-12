package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.event.TimeListener;
import org.moqucu.games.nightstalker.model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.moqucu.games.nightstalker.model.MovableObject.MAZE_JSON_FILE_NAME_CANNOT_BE_NULL;
import static org.moqucu.games.nightstalker.model.MovableObject.JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT;

public class MovableObjectTest {

    private final MovableObject movableObject = new MovableObject() {
    };

    @Test
    public void ofTypeGameObject() {

        assertThat(movableObject.getClass(), typeCompatibleWith(GameObject.class));
    }

    @Test
    public void hasVelocity() {

        assertThat(movableObject, hasProperty("velocity"));
    }

    @Test
    @DisplayName("Velocity property is of type Double.")
    public void velocityPropertyOfTypeDouble() {

        assertThat(movableObject.getVelocity(), isA(Double.class));
    }

    @Test
    public void hasDirection() {

        assertThat(movableObject, hasProperty("direction"));
    }

    @Test
    @DisplayName("Direction property is of type Direction.")
    public void directionPropertyOfTypeDirection() {

        assertThat(movableObject.getDirection(), isA(Direction.class));
    }

    @Test
    public void ofTypeTimeListener() {

        assertThat(movableObject.getClass(), typeCompatibleWith(TimeListener.class));
    }

    @Test
    public void hasPropertyInMotion() {

        assertThat(movableObject, hasProperty("inMotion"));
    }

    @Test
    @DisplayName("In motion property is of type Boolean.")
    public void inMotionPropertyOfTypeBoolean() {

        assertThat(movableObject.isInMotion(), isA(Boolean.class));
    }

    @Test
    public void hasPropertyMazeAlgorithm() {

        assertThat(movableObject, hasProperty("mazeAlgorithm"));
    }

    @Test
    @DisplayName("Maze algorithm property is of type MazeAlgorithm.")
    public void mazeAlgorithmPropertyOfTypeMazeAlgorithm() {

        assertThat(movableObject.getMazeAlgorithm(), isA(MazeAlgorithm.class));
    }

    @Test
    public void hasPropertyMazeGraphFileName() {

        assertThat(movableObject, hasProperty("mazeGraphFileName"));
    }

    @Test
    @DisplayName("Maze graph file name property is of type String.")
    public void mazeGraphFileNamePropertyOfTypeString() {

        assertThat(movableObject.getMazeGraphFileName(), isA(String.class));
    }

    @Test
    public void hasPropertyAbsMazeGraph() {

        assertThat(movableObject, hasProperty("absMazeGraph"));
    }

    @Test
    public void afterSettingCorrectMazeGraphFileNameAbsMazeGraphPropertyIsSet() {

        movableObject.setMazeGraphFileName("MazeGraphTest.json");

        assertThat(movableObject.getAbsMazeGraph(), isA(AbsMazeGraph.class));
    }

    @Test
    public void whenUsingNullForMazeGraphFileNameExceptionShallBeThrown() {

        final Exception exception = assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> movableObject.setMazeGraphFileName(null)
        );
        assertThat(exception.getMessage(), is(MAZE_JSON_FILE_NAME_CANNOT_BE_NULL));
    }

    @Test
    public void whenUsingEmptyStringForMazeGraphFileNameExceptionShallBeThrown() {

        final Exception exception = assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> movableObject.setMazeGraphFileName("")
        );
        assertThat(exception.getMessage(), is(JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT));
    }

    @Test
    public void setInMotionWorkOnlyWithDefinedDirection() {

        movableObject.setDirection(Direction.Undefined);
        movableObject.setMazeAlgorithm(MazeAlgorithm.None);
        assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> movableObject.setInMotion(true)
        );
        assertThat(movableObject.isInMotion(), is(false));

        movableObject.setX(32);
        movableObject.setY(32);
        movableObject.setVelocity(20);
        movableObject.setDirection(Direction.Left);
        movableObject.setMazeGraphFileName("MazeGraphTest.json");
        movableObject.setMazeAlgorithm(MazeAlgorithm.OuterRing);
        movableObject.setInMotion(true);
        assertThat(movableObject.isInMotion(), is(true));
        movableObject.setInMotion(false);
    }

    @Test
    @DisplayName("PropertyChangeListener can be added and are supported")
    public void propChangeListenerCanBeAddedAndAreSupported() {

        @SuppressWarnings("Convert2Lambda")
        final PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            @SneakyThrows
            public void propertyChange(PropertyChangeEvent evt) {
                throw new Exception(evt.getPropertyName());
            }
        };

        Exception exception;

        movableObject.addPropertyChangeListener(listener);

        assertThrows(Exception.class, () -> movableObject.setX(32));
        assertThrows(Exception.class, () -> movableObject.setY(32));
        exception = assertThrows(
                Exception.class,
                () -> movableObject.setVelocity(20)
        );
        assertThat(exception.getMessage(), is("velocity"));

        exception = assertThrows(
                Exception.class,
                () -> movableObject.setDirection(Direction.Left)
        );
        assertThat(exception.getMessage(), is("direction"));

        exception = assertThrows(
                Exception.class,
                () -> movableObject.setMazeGraphFileName("MazeGraphTest.json")
        );
        assertThat(exception.getMessage(), is("mazeGraphFileName"));

        exception = assertThrows(
                Exception.class,
                () -> movableObject.setMazeAlgorithm(MazeAlgorithm.OuterRing)
        );
        assertThat(exception.getMessage(), is("mazeAlgorithm"));

        exception = assertThrows(
                Exception.class,
                () -> movableObject.setInMotion(true)
        );
        assertThat(exception.getMessage(), is("inMotion"));
    }

}
