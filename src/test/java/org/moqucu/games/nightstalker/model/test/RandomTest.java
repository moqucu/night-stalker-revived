package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RandomTest {

    private final Random random = new Random();
    private static final String MAZE_GRAPH_NAME = "MazeGraphTest.json";
    private final AbsMazeGraph absMazeGraph;

    @SneakyThrows
    public RandomTest() {

        try (InputStream inputStream = getClass().getResourceAsStream(MAZE_GRAPH_NAME)) {

            final MazeGraph mazeGraph = new MazeGraph();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
        }
    }

    @Test
    public void from_32_00_Up_To_32_32_Down() {

        assertThat(
                random.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 0.0), Direction.Up)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Down))
        );
    }

    @Test
    public void testException() {

        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> random.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Undefined)
                )

        );
        assertThat(throwable.getMessage(), is("No opposite direction available to Undefined!"));
    }
}
