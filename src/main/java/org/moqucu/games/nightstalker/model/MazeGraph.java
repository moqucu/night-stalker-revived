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
import java.util.stream.Collectors;

@Log4j2
public class MazeGraph {

    private class UnrecognizedDirectionException extends RuntimeException {

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

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getX() == point.getX() && point2D.getY() > point.getY())
                .findAny()
                .isEmpty()
        )
            return point;

        List<Point2D> nodesBelow = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getX() == point.getX())
                .filter(point2D -> point2D.getY() > point.getY())
                .sorted(Comparator.comparing(Point2D::getY))
                .collect(Collectors.toList());

        if (log.isTraceEnabled()) {

            log.trace("Nodes below...");
            nodesBelow.forEach(log::trace);
        }

        Point2D furthestNodeBelow = nodesBelow.size() > 0 ? nodesBelow.get(0) : point;
        for (int i = 0; i + 1 < nodesBelow.size(); i++)

            if (adjacencyList.get(nodesBelow.get(i)).contains(nodesBelow.get(i + 1)))
                furthestNodeBelow = nodesBelow.get(i + 1);
            else
                break;

        log.debug("Returning furthest reachable node below: {}", furthestNodeBelow);

        return furthestNodeBelow;
    }

    private Point2D getFurthestReachableNodeAbove(Point2D point) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getX() == point.getX() && point2D.getY() < point.getY())
                .findAny()
                .isEmpty()
        )
            return point;

        List<Point2D> nodesAbove = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getX() == point.getX())
                .filter(point2D -> point2D.getY() < point.getY())
                .sorted(Comparator.comparing(Point2D::getY))
                .collect(Collectors.toList());

        if (log.isTraceEnabled()) {

            log.trace("Nodes above...");
            nodesAbove.forEach(log::trace);
        }

        Point2D furthestNodeAbove = nodesAbove.size() > 0 ? nodesAbove.get(nodesAbove.size() - 1) : point;

        for (int i = nodesAbove.size() - 1; i > 0; i--)

            if (adjacencyList.get(nodesAbove.get(i)).contains(nodesAbove.get(i - 1)))
                furthestNodeAbove = nodesAbove.get(i - 1);
            else
                break;

        log.debug("Returning furthest reachable node above: {}", furthestNodeAbove);

        return furthestNodeAbove;
    }

    private Point2D getFurthestReachableNodeToTheRight(Point2D point) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getY() == point.getY() && point2D.getX() > point.getX())
                .findAny()
                .isEmpty()
        )
            return point;

        List<Point2D> nodesToTheRight = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getY() == point.getY())
                .filter(point2D -> point2D.getX() > point.getX())
                .sorted(Comparator.comparing(Point2D::getX))
                .collect(Collectors.toList());

        if (log.isTraceEnabled()) {

            log.trace("Nodes to the right...");
            nodesToTheRight.forEach(log::trace);
        }

        Point2D furthestNodeToTheRight = nodesToTheRight.size() > 0 ? nodesToTheRight.get(0) : point;

        for (int i = 0; i + 1 < nodesToTheRight.size(); i++)

            if (adjacencyList.get(nodesToTheRight.get(i)).contains(nodesToTheRight.get(i + 1)))
                furthestNodeToTheRight = nodesToTheRight.get(i + 1);
            else
                break;

        log.debug("Returning furthest reachable node to the right: {}", furthestNodeToTheRight);

        return furthestNodeToTheRight;
    }

    private Point2D getFurthestReachableNodeToTheLeft(Point2D point) {

        if (isPointOnNode(point)
                && adjacencyList.get(point)
                .stream()
                .filter(point2D -> point2D.getY() == point.getY() && point2D.getX() < point.getX())
                .findAny()
                .isEmpty()
        )
            return point;

        List<Point2D> nodesToTheLeft = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getY() == point.getY())
                .filter(point2D -> point2D.getX() < point.getX())
                .sorted(Comparator.comparing(Point2D::getX))
                .collect(Collectors.toList());

        if (log.isTraceEnabled()) {

            log.trace("Nodes to the left...");
            nodesToTheLeft.forEach(log::trace);
        }

        Point2D furthestNodeToTheLeft
                = nodesToTheLeft.size() > 0 ? nodesToTheLeft.get(nodesToTheLeft.size() - 1) : point;

        for (int i = nodesToTheLeft.size() - 1; i > 0; i--)

            if (adjacencyList.get(nodesToTheLeft.get(i)).contains(nodesToTheLeft.get(i - 1)))
                furthestNodeToTheLeft = nodesToTheLeft.get(i - 1);
            else
                break;

        log.debug("Returning furthest reachable node to the left: {}", furthestNodeToTheLeft);

        return furthestNodeToTheLeft;
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
}
