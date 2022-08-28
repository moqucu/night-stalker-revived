package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbsMazeGraphTest {

    private final AbsMazeGraph absMazeGraph;

    @SneakyThrows
    public AbsMazeGraphTest() {

        try(InputStream inputStream = getClass().getResourceAsStream("MazeGraphTest.json")) {

            final MazeGraphV2 mazeGraph = new MazeGraphV2();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
        }
    }

    private AbsolutePosition createAbsPos(int x, int y) {

        return new AbsolutePosition(x * AbsMazeGraph.WIDTH, y * AbsMazeGraph.HEIGHT);
    }

    @Test
    @DisplayName("Node (1, 0) is closest to abs. position (32, 12)")
    public void oneZeroIsClosestNodeToAbsPos32_12() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        final AbsolutePosition _32_12 = new AbsolutePosition(32, 12);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_32_12), is(oneZero));
    }

    @Test
    @DisplayName("Node (1, 1) is closest to abs. position (32, 17)")
    public void oneZeroIsClosestNodeToAbsPos32_17() {

        final RelativePosition oneOne = new RelativePosition(1, 1);
        final AbsolutePosition _32_17 = new AbsolutePosition(32, 17);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_32_17), is(oneOne));
    }

    @Test
    @DisplayName("Node (0, 1) is closest to abs. position (13, 32)")
    public void zeroOneIsClosestNodeToAbsPos13_32() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        final AbsolutePosition _13_32 = new AbsolutePosition(13, 32);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_13_32), is(zeroOne));
    }

    @Test
    @DisplayName("Node (2, 1) is closest to abs. position (53, 32)")
    public void twoOneIsClosestNodeToAbsPos53_32() {

        final RelativePosition twoOne = new RelativePosition(2, 1);
        final AbsolutePosition _53_32 = new AbsolutePosition(53, 32);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_53_32), is(twoOne));
    }

    @Test
    @DisplayName("Node (3, 1) is closest to abs. position (96, 32)")
    public void threeOneIsClosestNodeToAbsPos96_32() {

        final RelativePosition threeOne = new RelativePosition(3, 1);
        final AbsolutePosition _96_32 = new AbsolutePosition(96, 32);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_96_32), is(threeOne));
    }

    @Test
    @DisplayName("Node (1, 2) is closest to abs. position (32, 55)")
    public void oneTwoIsClosestNodeToAbsPos32_55() {

        final RelativePosition oneTwo = new RelativePosition(1, 2);
        final AbsolutePosition _32_55 = new AbsolutePosition(32, 55);

        assertThat(absMazeGraph.getClosestNodeToAbsPos(_32_55), is(oneTwo));
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point with dummy direction should be (1, 1)")
    public void testWithFurthestReachableNodeWithDummyDirection() {

        AbsolutePosition startingPos = createAbsPos(1, 1);
        AbsolutePosition expectedFurthestPosToTheBottom = createAbsPos(1, 1);

        assertEquals(
                expectedFurthestPosToTheBottom,
                absMazeGraph.getFurthestReachablePosition(startingPos, Direction.OnTop)
        );
    }

    @Test
    @DisplayName("When starting with (0, 1), the furthest reachable point to the right should be (3, 1)")
    public void testFurthestReachableNodeToTheRight() {

        AbsolutePosition startingPoint = createAbsPos(0, 1);
        AbsolutePosition expectedFurthestPointToTheRight = createAbsPos(3, 1);

        assertEquals(
                expectedFurthestPointToTheRight,
                absMazeGraph.getFurthestReachablePosition(startingPoint, Direction.Right)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point to the bottom should be (1, 2)")
    public void testFurthestReachableNodeToTheBottom() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedFurthestPointToTheBottom = createAbsPos(1, 2);

        assertEquals(
                expectedFurthestPointToTheBottom,
                absMazeGraph.getFurthestReachablePosition(startingPoint, Direction.Down)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point to the left should be (0, 1)")
    public void testFurthestReachableNodeToTheLeft() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedFurthestPointToTheLeft = createAbsPos(0, 1);

        assertEquals(
                expectedFurthestPointToTheLeft,
                absMazeGraph.getFurthestReachablePosition(startingPoint, Direction.Left)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the furthest reachable point up should be (1, 0)")
    public void testWithFurthestReachableNodeUp() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedFurthestPointToTheTop = createAbsPos(1, 0);

        assertEquals(
                expectedFurthestPointToTheTop,
                absMazeGraph.getFurthestReachablePosition(startingPoint, Direction.Up)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point with dummy direction should trigger an exception")
    public void testClosestReachableNodeWithDummyDirection() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        assertThrows(
                MazeGraphV2.UnrecognizedDirectionException.class,
                () -> absMazeGraph.getClosestReachablePosition(startingPoint, Direction.OnTop)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the right should be (2, 1)")
    public void testClosestReachableNodeToTheRight() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedClosestReachableNodeToTheRight = createAbsPos(2, 1);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                absMazeGraph.getClosestReachablePosition(startingPoint, Direction.Right)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the left should be (0, 1)")
    public void testClosestReachableNodeToTheLeft() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedClosestReachableNodeToTheRight = createAbsPos(0, 1);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                absMazeGraph.getClosestReachablePosition(startingPoint, Direction.Left)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the bottom should be (1, 2)")
    public void testClosestReachableNodeToTheBottom() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedClosestReachableNodeToTheRight = createAbsPos(1, 2);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                absMazeGraph.getClosestReachablePosition(startingPoint, Direction.Down)
        );
    }

    @Test
    @DisplayName("When starting with (1, 1), the closest reachable point to the top should be (1, 0)")
    public void testClosestReachableNodeToTheTop() {

        AbsolutePosition startingPoint = createAbsPos(1, 1);
        AbsolutePosition expectedClosestReachableNodeToTheRight = createAbsPos(1, 0);

        assertEquals(
                expectedClosestReachableNodeToTheRight,
                absMazeGraph.getClosestReachablePosition(startingPoint, Direction.Up)
        );
    }
}
