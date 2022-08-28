package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.Direction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void presenceOfUpInstance() {

        assertThat(Direction.valueOf("Up"), is(notNullValue()));
        assertThat(Direction.valueOf("Up"), is(Direction.Up));
    }

    @Test
    public void presenceOfDownInstance() {

        assertThat(Direction.valueOf("Down"), is(notNullValue()));
        assertThat(Direction.valueOf("Down"), is(Direction.Down));
    }

    @Test
    public void presenceOfLeftInstance() {

        assertThat(Direction.valueOf("Left"), is(notNullValue()));
        assertThat(Direction.valueOf("Left"), is(Direction.Left));
    }

    @Test
    public void presenceOfRightInstance() {

        assertThat(Direction.valueOf("Right"), is(notNullValue()));
        assertThat(Direction.valueOf("Right"), is(Direction.Right));
    }

    @Test
    public void presenceOfUndefinedInstance() {

        assertThat(Direction.valueOf("Undefined"), is(notNullValue()));
        assertThat(Direction.valueOf("Undefined"), is(Direction.Undefined));
    }

    @Test
    @DisplayName("When point '2' has equal y coordinate but +x, direction to it should be 'Right'")
    public void testRightDirection() {

        assertEquals(
                Direction.Right,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(20, 0)),
                "Second point should be right from first point");
    }

    @Test
    @DisplayName("When point '2' has equal y coordinate but -x, direction to it should be 'Left'")
    public void testLeftDirection() {

        assertEquals(
                Direction.Left,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(-20, 0)),
                "Second point should be left from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x coordinate but +y, direction to it should be 'Down'")
    public void testDownDirection() {

        assertEquals(
                Direction.Down,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(0, +20)),
                "Second point should be down from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x coordinate but -y, direction to it should be 'Up'")
    public void testUpDirection() {

        assertEquals(
                Direction.Up,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(0, -20)),
                "Second point should be up from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x and y coordinates then it should be on top")
    public void testOnTopDirection() {

        assertEquals(
                Direction.OnTop,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(0, 0)),
                "Second point should be on top of first point");
    }

    @Test
    @DisplayName("Default to direction 'Right' when more than one coordinate differs.")
    public void testDefaultToRightDirection() {

        assertEquals(
                Direction.Right,
                Direction.calculateDirection(new AbsolutePosition(0, 0), new AbsolutePosition(+20, -20)),
                "Second point should be on top of first point");
    }
}
