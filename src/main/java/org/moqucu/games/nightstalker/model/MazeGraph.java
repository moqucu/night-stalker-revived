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
        Predicate<Point2D> predicate;
        Comparator<Point2D> comparator;

        switch (direction) {
            case Left:
                predicate = getPredicateForAnyNodeToTheLeft(roundedPoint);
                comparator = ascByHorizontalAxis;
                break;
            case Up:
                predicate = getPredicateForAnyNodeAbove(roundedPoint);
                comparator = ascByVerticalAxis;
                break;
            case Right:
                predicate = getPredicateForAnyNodeToTheRight(roundedPoint);
                comparator = ascByHorizontalAxis;
                break;
            case Down:
                predicate = getPredicateForAnyNodeBelow(roundedPoint);
                comparator = ascByVerticalAxis;
                break;
            default:
                return roundedPoint;
        }

        return returnClosestNodeInDirectionThatMatchesCondition(
                roundedPoint,
                direction,
                predicate,
                comparator
        );
    }

    private Predicate<Point2D> getPredicateForAnyNodeBelow(Point2D point, double maxOffset) {

        Predicate<Point2D> onSameVerticalAxis = point2D -> point2D.getX() == point.getX();
        Predicate<Point2D> belowTheGivenPoint = point2D -> point2D.getY() > point.getY() - maxOffset;

        return onSameVerticalAxis.and(belowTheGivenPoint);
    }

    private Predicate<Point2D> getPredicateForAnyNodeBelow(Point2D point) {

        return getPredicateForAnyNodeBelow(point, 0.);
    }

    private Predicate<Point2D> getPredicateForAnyNodeAbove(Point2D point, double maxOffset) {

        Predicate<Point2D> onSameVerticalAxis = point2D -> point2D.getX() == point.getX();
        Predicate<Point2D> aboveTheGivenPoint = point2D -> point2D.getY() < point.getY() + maxOffset;

        return onSameVerticalAxis.and(aboveTheGivenPoint);
    }

    private Predicate<Point2D> getPredicateForAnyNodeAbove(Point2D point) {

        return getPredicateForAnyNodeAbove(point, 0.);
    }

    private Predicate<Point2D> getPredicateForAnyNodeToTheRight(Point2D point, double maxOffset) {

        Predicate<Point2D> onSameHorizontalAxis = point2D -> point2D.getY() == point.getY();
        Predicate<Point2D> toTheRight = point2D -> point2D.getX() > point.getX() - maxOffset;

        return onSameHorizontalAxis.and(toTheRight);
    }

    private Predicate<Point2D> getPredicateForAnyNodeToTheRight(Point2D point) {

        return getPredicateForAnyNodeToTheRight(point, 0.);
    }

    private Predicate<Point2D> getPredicateForAnyNodeToTheLeft(Point2D point, double maxOffset) {

        Predicate<Point2D> onSameHorizontalAxis = point2D -> point2D.getY() == point.getY();
        Predicate<Point2D> toTheLeft = point2D -> point2D.getX() < point.getX() + maxOffset;

        return onSameHorizontalAxis.and(toTheLeft);
    }

    private Predicate<Point2D> getPredicateForAnyNodeToTheLeft(Point2D point) {

        return getPredicateForAnyNodeToTheLeft(point, 0.);
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

        if (isPointOnNode(point) && noNodesThatMatchCondition(point, getPredicateForAnyNodeBelow(point, maxOffset)))
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeBelow(point, maxOffset))
                .min(ascByVerticalAxis)
                .orElse(point);
    }

    private Point2D getClosestReachableNodeAbove(Point2D point, double maxOffset) {

        if (isPointOnNode(point) && noNodesThatMatchCondition(point, getPredicateForAnyNodeAbove(point, maxOffset)))
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeAbove(point, maxOffset))
                .max(ascByVerticalAxis)
                .orElse(point);
    }

    private Point2D getClosestReachableNodeToTheRight(Point2D point, double maxOffset) {

        if (isPointOnNode(point)
                && noNodesThatMatchCondition(point, getPredicateForAnyNodeToTheRight(point, maxOffset)))
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeToTheRight(point, maxOffset))
                .min(ascByHorizontalAxis)
                .orElse(point);
    }

    private Point2D getClosestReachableNodeToTheLeft(Point2D point, double maxOffset) {

        if (isPointOnNode(point) && noNodesThatMatchCondition(point, getPredicateForAnyNodeToTheLeft(point, maxOffset)))
            return point;

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeToTheLeft(point, maxOffset))
                .max(ascByHorizontalAxis)
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
