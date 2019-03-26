package org.moqucu.games.nightstalker.test;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class MazeGraphTest {

    private MazeGraph nightStalkerMazeGraph;

    @BeforeEach
    public void loadNightStalkerMazeGraphFromDisk() throws IOException {

        nightStalkerMazeGraph = new MazeGraph(
                (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph-night-stalker.json")
                        .getInputStream())
        );
    }

    @Test
    public void testGetReachableNodes() {

        List<Point2D> reachablePoints =
                nightStalkerMazeGraph.getReachableNodes(new Point2D(10.0 * 32 + 1, 3.0 * 32 ));
        Assertions.assertEquals(2, reachablePoints.size());
    }
}
