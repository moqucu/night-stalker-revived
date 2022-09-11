package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.event.TimeListener;
import org.moqucu.games.nightstalker.model.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.moqucu.games.nightstalker.model.MovableObject.MAZE_JSON_FILE_NAME_CANNOT_BE_NULL;
import static org.moqucu.games.nightstalker.model.MovableObject.JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT;

public class MovableObjectTest {

    private final MovableObject genericMovableObject = new MovableObject() {
    };

    @Test
    public void ofTypeGameObject() {

        assertThat(genericMovableObject.getClass(), typeCompatibleWith(GameObject.class));
    }

    @Test
    public void hasVelocity() {

        assertThat(genericMovableObject, hasProperty("velocity"));
    }

    @Test
    @DisplayName("Velocity property is of type Double.")
    public void velocityPropertyOfTypeDouble() {

        assertThat(genericMovableObject.getVelocity(), isA(Double.class));
    }

    @Test
    public void hasDirection() {

        assertThat(genericMovableObject, hasProperty("direction"));
    }

    @Test
    @DisplayName("Direction property is of type Direction.")
    public void directionPropertyOfTypeDirection() {

        assertThat(genericMovableObject.getDirection(), isA(Direction.class));
    }

    @Test
    public void ofTypeTimeListener() {

        assertThat(genericMovableObject.getClass(), typeCompatibleWith(TimeListener.class));
    }

    @Test
    public void hasPropertyInMotion() {

        assertThat(genericMovableObject, hasProperty("inMotion"));
    }

    @Test
    @DisplayName("In motion property is of type Boolean.")
    public void inMotionPropertyOfTypeBoolean() {

        assertThat(genericMovableObject.isInMotion(), isA(Boolean.class));
    }

    @Test
    public void hasPropertyMazeAlgorithm() {

        assertThat(genericMovableObject, hasProperty("mazeAlgorithm"));
    }

    @Test
    @DisplayName("Maze algorithm property is of type MazeAlgorithm.")
    public void mazeAlgorithmPropertyOfTypeMazeAlgorithm() {

        assertThat(genericMovableObject.getMazeAlgorithm(), isA(MazeAlgorithm.class));
    }

    @Test
    public void hasPropertyMazeGraphFileName() {

        assertThat(genericMovableObject, hasProperty("mazeGraphFileName"));
    }

    @Test
    @DisplayName("Maze graph file name property is of type String.")
    public void mazeGraphFileNamePropertyOfTypeString() {

        assertThat(genericMovableObject.getMazeGraphFileName(), isA(String.class));
    }

    @Test
    public void hasPropertyAbsMazeGraph() {

        assertThat(genericMovableObject, hasProperty("absMazeGraph"));
    }

    @Test
    public void afterSettingCorrectMazeGraphFileNameAbsMazeGraphPropertyIsSet() {

        genericMovableObject.setMazeGraphFileName("MazeGraphTest.json");

        assertThat(genericMovableObject.getAbsMazeGraph(), isA(AbsMazeGraph.class));
    }

    @Test
    public void whenUsingNullForMazeGraphFileNameExceptionShallBeThrown() {

        final Exception exception = assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> genericMovableObject.setMazeGraphFileName(null)
        );
        assertThat(exception.getMessage(), is(MAZE_JSON_FILE_NAME_CANNOT_BE_NULL));
    }

    @Test
    public void whenUsingEmptyStringForMazeGraphFileNameExceptionShallBeThrown() {

        final Exception exception = assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> genericMovableObject.setMazeGraphFileName("")
        );
        assertThat(exception.getMessage(), is(JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT));
    }

    @Test
    public void setInMotionWorkOnlyWithDefinedDirection() {

        genericMovableObject.setDirection(Direction.Undefined);
        genericMovableObject.setMazeAlgorithm(MazeAlgorithm.None);
        assertThrows(
                MovableObject.PreconditionNotMetForSettingObjectInMotionException.class,
                () -> genericMovableObject.setInMotion(true)
        );
        assertThat(genericMovableObject.isInMotion(), is(false));

        genericMovableObject.getAbsolutePosition().setX(32);
        genericMovableObject.getAbsolutePosition().setY(32);
        genericMovableObject.setVelocity(20);
        genericMovableObject.setDirection(Direction.Left);
        genericMovableObject.setMazeGraphFileName("MazeGraphTest.json");
        genericMovableObject.setMazeAlgorithm(MazeAlgorithm.OuterRing);
        genericMovableObject.setInMotion(true);
        assertThat(genericMovableObject.isInMotion(), is(true));
        genericMovableObject.setInMotion(false);
    }
}
