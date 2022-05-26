package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MazeGraphTest {

    /*
          (1,0)
            |
    (0,1)-(1,1)-(2,1)-(3,1)
            |
          (1,2)
     */
    private static final String mazeGraphAsJson = "[{\"node\":{\"x\":1,\"y\":1},\"adjacentNodes\":[{\"x\":2,\"y\":1}," +
            "{\"x\":1,\"y\":2},{\"x\":0,\"y\":1},{\"x\":1,\"y\":0}]},{\"node\":{\"x\":2,\"y\":1}," +
            "\"adjacentNodes\":[{\"x\":1,\"y\":1},{\"x\":3,\"y\":1}]},{\"node\":{\"x\":1,\"y\":2}," +
            "\"adjacentNodes\":[{\"x\":1,\"y\":1}]},{\"node\":{\"x\":3,\"y\":1},\"adjacentNodes\":[{\"x\":2,\"y\":1}]}," +
            "{\"node\":{\"x\":0,\"y\":1},\"adjacentNodes\":[{\"x\":1,\"y\":1}]},{\"node\":{\"x\":1,\"y\":0}," +
            "\"adjacentNodes\":[{\"x\":1,\"y\":1}]}]\n";

    private final MazeGraph mazeGraph
            = new MazeGraph(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));

    private Point2D createPoint(double x, double y) {

        return new Point2D(x * MazeGraph.WIDTH, y * MazeGraph.HEIGHT);
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point with dummy direction should be (1, 1)")
    void testWithFurthestReachableNodeWithDummyDirection() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedFurthestPointToTheBottom = createPoint(1.0, 1.0);

        assertEquals(
                expectedFurthestPointToTheBottom,
                mazeGraph.getFurthestReachableNode(startingPoint, Direction.OnTop)
        );
    }

    @Test
    @DisplayName("When starting with (0, 1), the furthest reachable point to the right should be (3, 1)")
    void testFurthestReachableNodeToTheRight() {

        Point2D startingPoint = createPoint(0.0, 1.0);
        Point2D expectedFurthestPointToTheRight = createPoint(3.0, 1.0);

        assertEquals(
                expectedFurthestPointToTheRight,
                mazeGraph.getFurthestReachableNode(startingPoint, Direction.Right)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point to the bottom should be (1, 2)")
    void testFurthestReachableNodeToTheBottom() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedFurthestPointToTheBottom = createPoint(1.0, 2.0);

        assertEquals(
                expectedFurthestPointToTheBottom,
                mazeGraph.getFurthestReachableNode(startingPoint, Direction.Down)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point to the left should be (0, 1)")
    void testFurthestReachableNodeToTheLeft() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedFurthestPointToTheLeft = createPoint(0.0, 1.0);

        assertEquals(
                expectedFurthestPointToTheLeft,
                mazeGraph.getFurthestReachableNode(startingPoint, Direction.Left)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point up should be (1, 0)")
    void testWithFurthestReachableNodeUp() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedFurthestPointToTheTop = createPoint(1.0, 0.0);

        assertEquals(
                expectedFurthestPointToTheTop,
                mazeGraph.getFurthestReachableNode(startingPoint, Direction.Up)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point with dummy direction should trigger an exception")
    void testClosestReachableNodeWithDummyDirection() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        assertThrows(
                MazeGraph.UnrecognizedDirectionException.class,
                () -> mazeGraph.getClosestReachableNode(startingPoint, Direction.OnTop, 0)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the right should be (2, 1)")
    void testClosestReachableNodeToTheRight() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedClosestReachableNodeToTheRight = createPoint(2.0, 1.0);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                mazeGraph.getClosestReachableNode(startingPoint, Direction.Right, 0.0)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the left should be (0, 1)")
    void testClosestReachableNodeToTheLeft() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedClosestReachableNodeToTheRight = createPoint(0.0, 1.0);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                mazeGraph.getClosestReachableNode(startingPoint, Direction.Left, 0.0)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the bottom should be (1, 2)")
    void testClosestReachableNodeToTheBottom() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedClosestReachableNodeToTheRight = createPoint(1.0, 2.0);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                mazeGraph.getClosestReachableNode(startingPoint, Direction.Down, 0.0)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the top should be (1, 0)")
    void testClosestReachableNodeToTheTop() {

        Point2D startingPoint = createPoint(1.0, 1.0);
        Point2D expectedClosestReachableNodeToTheRight = createPoint(1.0, 0.0);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                mazeGraph.getClosestReachableNode(startingPoint, Direction.Up, 0.0)
        );
    }

}
