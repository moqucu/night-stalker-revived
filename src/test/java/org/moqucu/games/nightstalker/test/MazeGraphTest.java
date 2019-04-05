package org.moqucu.games.nightstalker.test;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

class MazeGraphTest {

    private MazeGraph nightStalkerMazeGraph;

    @BeforeEach
    void loadNightStalkerMazeGraphFromDisk() throws IOException {

        nightStalkerMazeGraph = new MazeGraph(
                (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph-night-stalker.json")
                        .getInputStream())
        );
    }

    @Test
    void testGetReachableNodes() {

        Point2D reachablePoints = nightStalkerMazeGraph.getClosestReachableNode(
                new Point2D(10.0 * 32 + 1, 3.0 * 32 ),
                KeyCode.LEFT,
                0.0
        );
        Assert.assertNotNull(reachablePoints);
    }
}
