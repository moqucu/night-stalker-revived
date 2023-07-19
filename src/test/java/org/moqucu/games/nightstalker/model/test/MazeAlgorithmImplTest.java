package org.moqucu.games.nightstalker.model.test;

import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.AbsMazeGraph;
import org.moqucu.games.nightstalker.model.MazeGraph;

import java.io.InputStream;

public abstract class MazeAlgorithmImplTest {

    private static final String MAZE_GRAPH_NAME = "MazeGraphTest.json";

    protected final AbsMazeGraph absMazeGraph;

    @SneakyThrows
    public MazeAlgorithmImplTest() {

        try (InputStream inputStream = getClass().getResourceAsStream(MAZE_GRAPH_NAME)) {

            final MazeGraph mazeGraph = new MazeGraph();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
        }
    }
}
