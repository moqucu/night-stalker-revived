package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeGraphV2;
import org.moqucu.games.nightstalker.model.RelativePosition;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeGraphV2Test {

    private final MazeGraphV2 mazeGraph = new MazeGraphV2();

    @Test
    public void hasAdjacencyListProperty() {

        assertThat(mazeGraph, hasProperty("adjacencyList"));
    }

    @Test
    public void loadingJsonResultsInExpectedNodes() {

        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        final Map<RelativePosition, Set<RelativePosition>> adjacencyList = mazeGraph.getAdjacencyList();
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(1, 0)),
                        containsInAnyOrder(new RelativePosition(1, 1))
                )
        );
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(0, 1)),
                        containsInAnyOrder(new RelativePosition(1, 1))
                )
        );
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(1, 2)),
                        containsInAnyOrder(new RelativePosition(1, 1))
                )
        );
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(1, 1)),
                        containsInAnyOrder(
                                new RelativePosition(1, 0),
                                new RelativePosition(0, 1),
                                new RelativePosition(1, 2),
                                new RelativePosition(2, 1)
                        )
                )
        );
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(2, 1)),
                        containsInAnyOrder(
                                new RelativePosition(1, 1),
                                new RelativePosition(3, 1)
                        )
                )
        );
        assertThat(
                adjacencyList,
                hasEntry(
                        is(new RelativePosition(3, 1)),
                        containsInAnyOrder(new RelativePosition(2, 1))
                )
        );
    }

    @Test
    public void clearingAdjacencyListLeadsToEmptyMap() {
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(mazeGraph.getAdjacencyList().isEmpty(), is(false));
        mazeGraph.empty();
        assertThat(mazeGraph.getAdjacencyList().isEmpty(), is(true));
    }

    @Test
    public void oneOneIsTheClosedNodeToTheRightFromZeroOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(zeroOne, Direction.Right),
                is(oneOne)
        );
    }

    @Test
    public void twoOneIsTheClosedNodeToTheRightFromThreeOne() {

        final RelativePosition threeOne = new RelativePosition(3, 1);
        final RelativePosition twoOne = new RelativePosition(2, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(threeOne, Direction.Left),
                is(twoOne)
        );
    }

    @Test
    public void oneOneIsTheClosedNodeToTheBottomFromOneZero() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(oneZero, Direction.Down),
                is(oneOne)
        );
    }

    @Test
    public void oneOneIsTheClosedNodeToTheTopFromOneTwo() {

        final RelativePosition oneTwo = new RelativePosition(1, 2);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(oneTwo, Direction.Up),
                is(oneOne)
        );
    }

    @Test
    public void thereIsNothingToTheLeftOfZeroOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(zeroOne, Direction.Left),
                is(zeroOne)
        );
    }

    @Test
    public void thereIsNothingToTheTopOfOneZero() {

        final RelativePosition OneZero = new RelativePosition(1, 0);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(OneZero, Direction.Up),
                is(OneZero)
        );
    }

    @Test
    public void thereIsNothingToTheBottomOfOneTwo() {

        final RelativePosition OneTwo = new RelativePosition(1, 2);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(OneTwo, Direction.Down),
                is(OneTwo)
        );
    }

    @Test
    public void thereIsNothingToTheRightOfThreeOne() {

        final RelativePosition threeOne = new RelativePosition(3, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getClosestReachableNode(threeOne, Direction.Up),
                is(threeOne)
        );
    }

    @Test
    public void usingAnUnrecognizedDirectionLeadsToException() {

        final RelativePosition oneOne = new RelativePosition(1, 1);
        assertThrows(
                MazeGraphV2.UnrecognizedDirectionException.class,
                () -> mazeGraph.getClosestReachableNode(oneOne, Direction.Undefined)
        );
    }

    @Test
    public void threeOneIsFurthestReachableNodeToTheRightFromZeroOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        final RelativePosition threeOne = new RelativePosition(3, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(zeroOne, Direction.Right),
                is(threeOne)
        );
    }

    @Test
    public void zeroOneIsFurthestReachableNodeToTheLeftFromZeroOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(zeroOne, Direction.Left),
                is(zeroOne)
        );
    }

    @Test
    public void zeroOneIsFurthestReachableNodeToTheLeftFromThreeOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        final RelativePosition threeOne = new RelativePosition(3, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(threeOne, Direction.Left),
                is(zeroOne)
        );
    }

    @Test
    public void threeOneIsFurthestReachableNodeToTheRightFromThreeOne() {

        final RelativePosition threeOne = new RelativePosition(3, 1);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(threeOne, Direction.Right),
                is(threeOne)
        );
    }

    @Test
    public void oneZeroOneIsFurthestReachableNodeToTheTopFromOneTwo() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        final RelativePosition oneTwo = new RelativePosition(1, 2);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(oneTwo, Direction.Up),
                is(oneZero)
        );
    }

    @Test
    public void oneTwoOneIsFurthestReachableNodeToTheBottomFromOneTwo() {

        final RelativePosition oneTwo = new RelativePosition(1, 2);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(oneTwo, Direction.Down),
                is(oneTwo)
        );
    }

    @Test
    public void oneTwoIsFurthestReachableNodeToTheBottomFromOneZero() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        final RelativePosition oneTwo = new RelativePosition(1, 2);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(oneZero, Direction.Down),
                is(oneTwo)
        );
    }

    @Test
    public void oneZeroIsFurthestReachableNodeToTheTopFromOneZero() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        mazeGraph.loadFromJson(getClass().getResourceAsStream("MazeGraphTest.json"));
        assertThat(
                mazeGraph.getFurthestReachableNode(oneZero, Direction.Up),
                is(oneZero)
        );
    }
}
