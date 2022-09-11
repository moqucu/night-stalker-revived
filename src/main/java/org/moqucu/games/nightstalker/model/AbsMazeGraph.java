package org.moqucu.games.nightstalker.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AbsMazeGraph {

    public static class PositionOutOfBoundsException extends RuntimeException {

        PositionOutOfBoundsException(String message) {

            super(message);
        }
    }

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

    public RelativePosition getClosestNodeToAbsPos(AbsolutePosition absolutePosition, Direction direction) {

        if (!isWithinBounds(absolutePosition))
            throw new PositionOutOfBoundsException(
                    String.format(
                            "Absolute position with coordinates x=%f and y=%f is outside the graph's boundary!",
                            absolutePosition.getX(),
                            absolutePosition.getY()
                    )
            );

        final Map<Double, RelativePosition> distanceToRelPos = new HashMap<>();

        Set<RelativePosition> relevantSetOfRelativePositionsGivenTheDirection;
        switch (direction) {
            case Right:
                relevantSetOfRelativePositionsGivenTheDirection =
                        allRelativePosMappedToAbsPos.keySet().stream().filter(
                                relPos -> relPos.getY() * HEIGHT == absolutePosition.getY()
                                        && relPos.getX() * WIDTH >= absolutePosition.getX()
                        ).collect(Collectors.toSet());
                break;
            case Left:
                relevantSetOfRelativePositionsGivenTheDirection =
                        allRelativePosMappedToAbsPos.keySet().stream().filter(
                                relPos -> relPos.getY() * HEIGHT == absolutePosition.getY()
                                        && relPos.getX() * WIDTH <= absolutePosition.getX()
                        ).collect(Collectors.toSet());
                break;
            case Down:
                relevantSetOfRelativePositionsGivenTheDirection =
                        allRelativePosMappedToAbsPos.keySet().stream().filter(
                                relPos -> relPos.getX() * HEIGHT == absolutePosition.getX()
                                        && relPos.getY() * WIDTH >= absolutePosition.getY()
                        ).collect(Collectors.toSet());
                break;
            case Up:
                relevantSetOfRelativePositionsGivenTheDirection =
                        allRelativePosMappedToAbsPos.keySet().stream().filter(
                                relPos -> relPos.getX() * HEIGHT == absolutePosition.getX()
                                        && relPos.getY() * WIDTH <= absolutePosition.getY()
                        ).collect(Collectors.toSet());
                break;
            default:
                relevantSetOfRelativePositionsGivenTheDirection = allRelativePosMappedToAbsPos.keySet();
        }

        relevantSetOfRelativePositionsGivenTheDirection.forEach(key -> distanceToRelPos.put(
                Math.abs(allRelativePosMappedToAbsPos.get(key).getX() - absolutePosition.getX())
                        + Math.abs(allRelativePosMappedToAbsPos.get(key).getY() - absolutePosition.getY()),
                key
        ));

        final Double closestDistance = distanceToRelPos.keySet().stream().sorted().findFirst().orElseThrow();

        return distanceToRelPos.get(closestDistance);
    }

    public AbsolutePosition getFurthestReachablePosition(AbsolutePosition position, Direction direction) {

        if (!isWithinBounds(position))
            throw new PositionOutOfBoundsException(
                    String.format(
                            "Absolute position with coordinates x=%f and y=%f is outside the graph's boundary!",
                            position.getX(),
                            position.getY()
                    )
            );

        try {

            final RelativePosition closestRelativePosition
                    = getClosestNodeToAbsPos(position, Direction.opposite(direction));
            final RelativePosition furthestReachableNode
                    = mazeGraph.getFurthestReachableNode(closestRelativePosition, direction);

            return new AbsolutePosition(
                    furthestReachableNode.getX() * WIDTH,
                    furthestReachableNode.getY() * HEIGHT
            );
        } catch (NoSuchElementException noSuchElementException) {

            return position;
        }
    }

    public AbsolutePosition getClosestReachablePosition(AbsolutePosition position, Direction direction) {

        if (!isWithinBounds(position))
            throw new PositionOutOfBoundsException(
                    String.format(
                            "Absolute position with coordinates x=%f and y=%f is outside the graph's boundary!",
                            position.getX(),
                            position.getY()
                    )
            );

        AbsolutePosition closestAbsolutePosition;

        try {
            final RelativePosition closestRelativePosition
                    = getClosestNodeToAbsPos(position, Direction.opposite(direction));
            final RelativePosition closestReachableNode
                    = mazeGraph.getClosestReachableNode(closestRelativePosition, direction);

            closestAbsolutePosition = new AbsolutePosition(
                    closestReachableNode.getX() * WIDTH,
                    closestReachableNode.getY() * HEIGHT
            );

        } catch (NoSuchElementException noSuchElementException) {
            closestAbsolutePosition = position;
        }

        switch (direction) {
            case Right:
            case Left:
                if (closestAbsolutePosition.getY() == position.getY())
                    return closestAbsolutePosition;
                else
                    return position;
            case Up:
            case Down:
                if (closestAbsolutePosition.getX() == position.getX())
                    return closestAbsolutePosition;
                else
                    return position;
            default:
                throw new MazeGraphV2.UnrecognizedDirectionException(
                        String.format("Direction %s unrecognized!", direction)
                );
        }
    }

    public boolean isWithinBounds(AbsolutePosition position) {

        final AtomicBoolean isWithinBounds = new AtomicBoolean(false);

        final Set<RelativePosition> positionsThatMatchXCoordinate = mazeGraph.getAdjacencyList()
                .keySet()
                .stream()
                .filter(relativePosition -> relativePosition.getX() * WIDTH * 1.0 == position.getX())
                .collect(Collectors.toSet());
        positionsThatMatchXCoordinate.forEach(
                xMatchingPosition -> {
                    final double minYPosition = mazeGraph
                            .getFurthestReachableNode(xMatchingPosition, Direction.Up)
                            .getY() * HEIGHT * 1.0;
                    final double maxYPosition = mazeGraph
                            .getFurthestReachableNode(xMatchingPosition, Direction.Down)
                            .getY() * HEIGHT * 1.0;
                    if (position.getY() >= minYPosition && position.getY() <= maxYPosition)
                        isWithinBounds.set(true);
                }
        );

        final Set<RelativePosition> positionsThatMatchYCoordinate = mazeGraph.getAdjacencyList()
                .keySet()
                .stream()
                .filter(relativePosition -> relativePosition.getY() * HEIGHT * 1.0 == position.getY())
                .collect(Collectors.toSet());
        positionsThatMatchYCoordinate.forEach(
                yMatchingPosition -> {
                    final double minXPosition = mazeGraph
                            .getFurthestReachableNode(yMatchingPosition, Direction.Left)
                            .getX() * WIDTH * 1.0;
                    final double maxXPosition = mazeGraph
                            .getFurthestReachableNode(yMatchingPosition, Direction.Right)
                            .getX() * WIDTH * 1.0;
                    if (position.getX() >= minXPosition && position.getX() <= maxXPosition)
                        isWithinBounds.set(true);
                }
        );

        return isWithinBounds.get();
    }
}
