package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Point2D;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log4j2
public class MazeGraph {

    private static class UnrecognizedDirectionException extends RuntimeException {

        UnrecognizedDirectionException(String message) {
            super(message);
        }
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AdjacencyList {

        @Data
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class XY {

            private double x;
            private double y;
        }

        private XY node;
        private List<XY> adjacentNodes;
    }

    private Map<Point2D, List<Point2D>> adjacencyList = new HashMap<>();

    private Comparator<Point2D> ascByVerticalAxis = Comparator.comparing(Point2D::getY);
    private Comparator<Point2D> ascByHorizontalAxis = Comparator.comparing(Point2D::getX);

    @SneakyThrows
    public MazeGraph(InputStream adjacencyListJsonArray) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<AdjacencyList> adjacencyLists
                = objectMapper.readValue(adjacencyListJsonArray, new TypeReference<List<AdjacencyList>>() {
        });

        adjacencyLists.forEach(adjacencyListItem -> adjacencyListItem.getAdjacentNodes().forEach(node ->
                addEge(
                        new Point2D(adjacencyListItem.node.x * 32, adjacencyListItem.node.y * 32),
                        new Point2D(node.x * 32, node.y * 32)
                )));
    }

    private void addDirectedEdge(Point2D node, Point2D adjacentNode) {

        if (!adjacencyList.containsKey(node))
            adjacencyList.put(node, new LinkedList<>());

        if (!adjacencyList.get(node).contains(adjacentNode))
            adjacencyList.get(node).add(adjacentNode);
    }

    private void addEge(Point2D node, Point2D adjacentNode) {

        addDirectedEdge(node, adjacentNode);
        addDirectedEdge(adjacentNode, node);
    }

    public Map<Point2D, List<Point2D>> getAdjacencyList() {

        return adjacencyList;
    }

    private boolean isPointOnNode(Point2D point) {

        return adjacencyList.containsKey(point);
    }

    public Point2D getFurthestReachableNode(Point2D point, Direction direction) {

        Point2D roundedPoint = new Point2D(Math.round(point.getX()), Math.round(point.getY()));

        switch (direction) {
            case Left:
                return getFurthestReachableNodeToTheLeft(roundedPoint);
            case Up:
                return getFurthestReachableNodeAbove(roundedPoint);
            case Right:
                return getFurthestReachableNodeToTheRight(roundedPoint);
            case Down:
                return getFurthestReachableNodeBelow(roundedPoint);
            default:
                throw new UnrecognizedDirectionException(
                        MessageFormat.format("Direction {0} is not a valid input for this method!", direction)
                );
        }
    }

    private Point2D getFurthestReachableNodeBelow(Point2D point) {

        Predicate<Point2D> onSameVerticalAxis = point2D -> point2D.getX() == point.getX();
        Predicate<Point2D> belowTheGivenPoint = point2D -> point2D.getY() > point.getY();
        Predicate<Point2D> anyNodeBelow = onSameVerticalAxis.and(belowTheGivenPoint);

        return returnClosestNodeInDirectionThatMatchesCondition(
                point,
                Direction.Down,
                anyNodeBelow,
                ascByVerticalAxis
        );
    }

    private Point2D getFurthestReachableNodeAbove(Point2D point) {

        Predicate<Point2D> onSameVerticalAxis = point2D -> point2D.getX() == point.getX();
        Predicate<Point2D> aboveTheGivenPoint = point2D -> point2D.getY() < point.getY();
        Predicate<Point2D> anyNodeAbove = onSameVerticalAxis.and(aboveTheGivenPoint);

        return returnClosestNodeInDirectionThatMatchesCondition(
                point,
                Direction.Up,
                anyNodeAbove,
                ascByVerticalAxis
        );
    }

    private Point2D getFurthestReachableNodeToTheRight(Point2D point) {

        Predicate<Point2D> onSameHorizontalAxis = point2D -> point2D.getY() == point.getY();
        Predicate<Point2D> toTheRight = point2D -> point2D.getX() > point.getX();
        Predicate<Point2D> anyNodeToTheRight = onSameHorizontalAxis.and(toTheRight);

        return returnClosestNodeInDirectionThatMatchesCondition(
                point,
                Direction.Right,
                anyNodeToTheRight,
                ascByHorizontalAxis
        );
    }

    private Point2D getFurthestReachableNodeToTheLeft(Point2D point) {

        Predicate<Point2D> onSameHorizontalAxis = point2D -> point2D.getY() == point.getY();
        Predicate<Point2D> toTheLeft = point2D -> point2D.getX() < point.getX();
        Predicate<Point2D> anyNodeToTheLeft = onSameHorizontalAxis.and(toTheLeft);

        return returnClosestNodeInDirectionThatMatchesCondition(
                point,
                Direction.Left,
                anyNodeToTheLeft,
                ascByHorizontalAxis
        );
    }

    private Point2D returnClosestNodeInDirectionThatMatchesCondition(
            Point2D point,
            Direction direction,
            Predicate<Point2D> condition,
            Comparator<Point2D> comparator
    ) {

        if (isPointOnNode(point) && noNodesThatMatchCondition(point, condition))
            return point;

        List<Point2D> sortedListOfNodesThatMatchCondition
                = returnAllNodesThatMatchConditionAsOrderedList(condition, comparator);

        logAllNodesIndividuallyWhenInTraceMode(direction, sortedListOfNodesThatMatchCondition);

        switch (direction) {
            case Down:
            case Right:
                return findAndReturnFurthestNodesInAscOrder(direction, point, sortedListOfNodesThatMatchCondition);
            default: // Up & Left
                return findAndReturnFurthestNodesInDescOrder(direction, point, sortedListOfNodesThatMatchCondition);
        }
    }

    public Point2D getClosestReachableNode(Point2D point, Direction direction, double maxOffset) {

        Point2D roundedPoint = new Point2D(Math.round(point.getX()), Math.round(point.getY()));

        switch (direction) {

            case Left:
                return getClosestReachableNodeToTheLeft(roundedPoint, maxOffset);
            case Up:
                return getClosestReachableNodeAbove(roundedPoint, maxOffset);
            case Right:
                return getClosestReachableNodeToTheRight(roundedPoint, maxOffset);
            case Down:
                return getClosestReachableNodeBelow(roundedPoint, maxOffset);
            default:
                throw new UnrecognizedDirectionException(
                        MessageFormat.format("Direction {0} is not a valid input for this method!", direction)
                );
        }
    }

    private Point2D getClosestReachableNodeBelow(Point2D point, double maxOffset) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getX() == point.getX() && point2D.getY() > point.getY() - maxOffset)
                .findAny()
                .isEmpty()
        )
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getX() == point.getX())
                .filter(point2D -> point2D.getY() > point.getY() - maxOffset)
                .min(Comparator.comparing(Point2D::getY))
                .orElse(point);
    }

    private Point2D getClosestReachableNodeAbove(Point2D point, double maxOffset) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getX() == point.getX() && point2D.getY() < point.getY() + maxOffset)
                .findAny()
                .isEmpty()
        )
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getX() == point.getX())
                .filter(point2D -> point2D.getY() < point.getY() + maxOffset)
                .max(Comparator.comparing(Point2D::getY))
                .orElse(point);
    }

    private Point2D getClosestReachableNodeToTheRight(Point2D point, double maxOffset) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getY() == point.getY() && point2D.getX() > point.getX() - maxOffset)
                .findAny()
                .isEmpty()
        )
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getY() == point.getY())
                .filter(point2D -> point2D.getX() > point.getX() - maxOffset)
                .min(Comparator.comparing(Point2D::getX))
                .orElse(point);
    }

    private Point2D getClosestReachableNodeToTheLeft(Point2D point, double maxOffset) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getY() == point.getY() && point2D.getX() < point.getX() + maxOffset)
                .findAny()
                .isEmpty()
        )
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getY() == point.getY())
                .filter(point2D -> point2D.getX() < point.getX() + maxOffset)
                .max(Comparator.comparing(Point2D::getX))
                .orElse(point);
    }

    private void logAllNodesIndividuallyWhenInTraceMode(Direction direction, List<Point2D> nodes) {

        if (log.isTraceEnabled()) {

            log.trace("Nodes {} from here...", direction);
            nodes.forEach(log::trace);
        }
    }

    private Point2D findAndReturnFurthestNodesInAscOrder(
            Direction direction,
            Point2D point,
            List<Point2D> nodes
    ) {

        Point2D furthestNode = nodes.size() > 0 ? nodes.get(0) : point;

        for (int i = 0; i + 1 < nodes.size(); i++)

            if (adjacencyList.get(nodes.get(i)).contains(nodes.get(i + 1)))
                furthestNode = nodes.get(i + 1);
            else
                break;

        log.debug("Returning furthest reachable node in direction {}: {}", direction, furthestNode);

        return furthestNode;
    }

    private Point2D findAndReturnFurthestNodesInDescOrder(
            Direction direction,
            Point2D point,
            List<Point2D> nodes
    ) {

        Point2D furthestNode = nodes.size() > 0 ? nodes.get(nodes.size() - 1) : point;

        for (int i = nodes.size() - 1; i > 0; i--)

            if (adjacencyList.get(nodes.get(i)).contains(nodes.get(i - 1)))
                furthestNode = nodes.get(i - 1);
            else
                break;

        log.debug("Returning furthest reachable node in direction {}: {}", direction, furthestNode);

        return furthestNode;
    }

    private boolean noNodesThatMatchCondition(Point2D point, Predicate<Point2D> condition) {

        return adjacencyList.get(point)
                .stream()
                .filter(condition)
                .findAny()
                .isEmpty();
    }

    private List<Point2D> returnAllNodesThatMatchConditionAsOrderedList(
            Predicate<Point2D> condition,
            Comparator<Point2D> comparator
    ) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(condition)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
