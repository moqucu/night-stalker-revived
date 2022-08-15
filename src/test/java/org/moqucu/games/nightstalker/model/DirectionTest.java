package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    @DisplayName("When point '2' has equal y coordinate but +x, direction to it should be 'Right'")
    public void testRightDirection() {

        assertEquals(
                Direction.Right,
                Direction.calculateDirection(Point2D.ZERO, new Point2D(20, 0)),
                "Second point should be right from first point");
    }

    @Test
    @DisplayName("When point '2' has equal y coordinate but -x, direction to it should be 'Left'")
    public void testLeftDirection() {

        assertEquals(
                Direction.Left,
                Direction.calculateDirection(Point2D.ZERO, new Point2D(-20, 0)),
                "Second point should be left from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x coordinate but +y, direction to it should be 'Down'")
    public void testDownDirection() {

        assertEquals(
                Direction.Down,
                Direction.calculateDirection(Point2D.ZERO, new Point2D(0, +20)),
                "Second point should be down from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x coordinate but -y, direction to it should be 'Up'")
    public void testUpDirection() {

        assertEquals(
                Direction.Up,
                Direction.calculateDirection(Point2D.ZERO, new Point2D(0, -20)),
                "Second point should be up from first point");
    }

    @Test
    @DisplayName("When point '2' has equal x and y coordinates then it should be on top")
    public void testOnTopDirection() {

        assertEquals(
                Direction.OnTop,
                Direction.calculateDirection(Point2D.ZERO, Point2D.ZERO),
                "Second point should be on top of first point");
    }

    @Test
    @DisplayName("Default to direction 'Right' when more than one coordinate differs.")
    public void testDefaultToRightDirection() {

        assertEquals(
                Direction.Right,
                Direction.calculateDirection(Point2D.ZERO, new Point2D(+20, -20)),
                "Second point should be on top of first point");
    }
}
