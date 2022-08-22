package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeGraphV2;
import org.moqucu.games.nightstalker.model.RelativePosition;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeGraphV2Test {

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

    private final MazeGraphV2 mazeGraph = new MazeGraphV2();

    @Test
    public void hasAdjacencyListProperty() {

        assertThat(mazeGraph, hasProperty("adjacencyList"));
    }

    @Test
    public void loadingJsonResultsInExpectedNodes() {

        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
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
        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
        assertThat(mazeGraph.getAdjacencyList().isEmpty(), is(false));
        mazeGraph.empty();
        assertThat(mazeGraph.getAdjacencyList().isEmpty(), is(true));
    }

    @Test
    public void oneOneIsTheClosedNodeToTheRightFromZeroOne() {

        final RelativePosition zeroOne = new RelativePosition(0, 1);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
        assertThat(
                mazeGraph.getClosestReachableNode(zeroOne, Direction.Right),
                is(oneOne)
        );
    }

    @Test
    public void twoOneIsTheClosedNodeToTheRightFromThreeOne() {

        final RelativePosition threeOne = new RelativePosition(3, 1);
        final RelativePosition twoOne = new RelativePosition(2, 1);
        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
        assertThat(
                mazeGraph.getClosestReachableNode(threeOne, Direction.Left),
                is(twoOne)
        );
    }

    @Test
    public void oneOneIsTheClosedNodeToTheBottomFromOneZero() {

        final RelativePosition oneZero = new RelativePosition(1, 0);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
        assertThat(
                mazeGraph.getClosestReachableNode(oneZero, Direction.Down),
                is(oneOne)
        );
    }

    @Test
    public void oneOneIsTheClosedNodeToTheTopFromOneTwo() {

        final RelativePosition oneTwo = new RelativePosition(1, 2);
        final RelativePosition oneOne = new RelativePosition(1, 1);
        mazeGraph.loadFromJson(new ByteArrayInputStream(mazeGraphAsJson.getBytes()));
        assertThat(
                mazeGraph.getClosestReachableNode(oneTwo, Direction.Up),
                is(oneOne)
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
}
