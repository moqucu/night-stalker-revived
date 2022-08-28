package org.moqucu.games.nightstalker.model;

import java.util.HashMap;
import java.util.Map;

public class AbsMazeGraph {

    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private final MazeGraphV2 mazeGraph;

    private final Map<RelativePosition, AbsolutePosition> allRelativePosMappedToAbsPos = new HashMap<>();

    public AbsMazeGraph(MazeGraphV2 mazeGraph) {

        this.mazeGraph = mazeGraph;
        mazeGraph.getAdjacencyList().keySet().forEach(
                relativePos -> allRelativePosMappedToAbsPos.put(
                        relativePos,
                        new AbsolutePosition(relativePos.getX() * WIDTH, relativePos.getY() * HEIGHT)
                )
        );
    }

    public RelativePosition getClosestNodeToAbsPos(AbsolutePosition absolutePosition) {

        final Map<Double, RelativePosition> distanceToRelPos = new HashMap<>();
        allRelativePosMappedToAbsPos.keySet().forEach(key -> distanceToRelPos.put(
                Math.abs(allRelativePosMappedToAbsPos.get(key).getX() - absolutePosition.getX())
                        + Math.abs(allRelativePosMappedToAbsPos.get(key).getY() - absolutePosition.getY()),
                key
        ));
        final Double closestDistance = distanceToRelPos.keySet().stream().sorted().findFirst().orElseThrow();

        return distanceToRelPos.get(closestDistance);
    }

    public AbsolutePosition getFurthestReachablePosition(AbsolutePosition position, Direction direction) {

        final RelativePosition closestRelativePosition = getClosestNodeToAbsPos(position);
        final RelativePosition furthestReachableNode
                = mazeGraph.getFurthestReachableNode(closestRelativePosition, direction);

        return new AbsolutePosition(
                furthestReachableNode.getX() * WIDTH,
                furthestReachableNode.getY() * HEIGHT
        );
    }

    public AbsolutePosition getClosestReachablePosition(AbsolutePosition position, Direction direction) {


        final RelativePosition closestRelativePosition = getClosestNodeToAbsPos(position);
        final RelativePosition closestReachableNode
                = mazeGraph.getClosestReachableNode(closestRelativePosition, direction);

        return new AbsolutePosition(
                closestReachableNode.getX() * WIDTH,
                closestReachableNode.getY() * HEIGHT
        );
    }
}
