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
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class MazeGraph {

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

    public List<Point2D> getFurthestReachableNodes(Point2D point) {

        Point2D roundedPoint = new Point2D(Math.round(point.getX()), Math.round(point.getY()));

        if (isPointOnNode(roundedPoint))
            return adjacencyList.get(roundedPoint);

        List<Point2D> reachableNodes = new ArrayList<>();

        Set<Point2D> xAlignedNeighbors = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getX() == roundedPoint.getX())
                .collect(Collectors.toSet());

        if (xAlignedNeighbors.size() != 0) {

            List<Point2D> nodesBelow = xAlignedNeighbors
                    .stream()
                    .filter(point2D -> point2D.getY() > roundedPoint.getY())
                    .sorted(Comparator.comparing(Point2D::getY))
                    .collect(Collectors.toList());

            if (log.isTraceEnabled()) {

                log.trace("Nodes below...");
                nodesBelow.forEach(log::trace);
            }

            if (nodesBelow.size() == 1) {

                reachableNodes.addAll(nodesBelow);
                log.debug("Added furthest reachable node below: {}", nodesBelow.get(0));
            } else if (nodesBelow.size() > 1) {

                Point2D furthestNodeBelow = nodesBelow.get(0);
                for (int i = 0; i + 1 < nodesBelow.size(); i++)

                    if (adjacencyList.get(nodesBelow.get(i)).contains(nodesBelow.get(i + 1)))
                        furthestNodeBelow = nodesBelow.get(i + 1);
                    else
                        break;

                reachableNodes.add(furthestNodeBelow);
                log.debug("Added furthest reachable node below: {}", furthestNodeBelow);
            }

            List<Point2D> nodesAbove = xAlignedNeighbors
                    .stream()
                    .filter(point2D -> point2D.getY() < roundedPoint.getY())
                    .sorted(Comparator.comparing(Point2D::getY))
                    .collect(Collectors.toList());

            if (log.isTraceEnabled()) {

                log.trace("Nodes above...");
                nodesAbove.forEach(log::trace);
            }

            if (nodesAbove.size() == 1) {

                reachableNodes.addAll(nodesAbove);
                log.debug("Added furthest reachable node above: {}", nodesAbove.get(0));
            } else if (nodesAbove.size() > 1) {

                Point2D furthestNodeAbove = nodesAbove.get(nodesAbove.size() - 1);

                for (int i = nodesAbove.size() - 1; i > 0; i--)

                    if (adjacencyList.get(nodesAbove.get(i)).contains(nodesAbove.get(i - 1)))
                        furthestNodeAbove = nodesAbove.get(i - 1);
                    else
                        break;

                reachableNodes.add(furthestNodeAbove);
                log.debug("Added furthest reachable node above: {}", furthestNodeAbove);
            }
        }

        Set<Point2D> yAlignedNeighbors = adjacencyList
                .keySet()
                .stream()
                .filter(point2D -> point2D.getY() == roundedPoint.getY())
                .collect(Collectors.toSet());

        if (yAlignedNeighbors.size() != 0) {

            List<Point2D> nodesToTheRight = yAlignedNeighbors
                    .stream()
                    .filter(point2D -> point2D.getX() > roundedPoint.getX())
                    .sorted(Comparator.comparing(Point2D::getX))
                    .collect(Collectors.toList());

            if (log.isTraceEnabled()) {

                log.trace("Nodes to the right...");
                nodesToTheRight.forEach(log::trace);
            }

            if (nodesToTheRight.size() == 1) {

                reachableNodes.addAll(nodesToTheRight);
                log.debug("Added furthest reachable node to the right: {}", nodesToTheRight.get(0));
            } else if (nodesToTheRight.size() > 1) {

                Point2D furthestNodeToTheRight = nodesToTheRight.get(0);
                for (int i = 0; i + 1 < nodesToTheRight.size(); i++)

                    if (adjacencyList.get(nodesToTheRight.get(i)).contains(nodesToTheRight.get(i + 1)))
                        furthestNodeToTheRight = nodesToTheRight.get(i + 1);
                    else
                        break;

                reachableNodes.add(furthestNodeToTheRight);
                log.debug("Added furthest reachable node to the right: {}", furthestNodeToTheRight);
            }

            List<Point2D> nodesToTheLeft = yAlignedNeighbors
                    .stream()
                    .filter(point2D -> point2D.getX() < roundedPoint.getX())
                    .sorted(Comparator.comparing(Point2D::getX))
                    .collect(Collectors.toList());

            if (log.isTraceEnabled()) {

                log.trace("Nodes to the left...");
                nodesToTheLeft.forEach(log::trace);
            }

            if (nodesToTheLeft.size() == 1) {

                reachableNodes.addAll(nodesToTheLeft);
                log.info("Added furthest reachable node to the left: {}", nodesToTheLeft.get(0));
            } else if (nodesToTheLeft.size() > 1) {

                Point2D furthestNodeToTheLeft = nodesToTheLeft.get(nodesToTheLeft.size() - 1);

                for (int i = nodesToTheLeft.size() - 1; i > 0; i--)

                    if (adjacencyList.get(nodesToTheLeft.get(i)).contains(nodesToTheLeft.get(i - 1)))
                        furthestNodeToTheLeft = nodesToTheLeft.get(i - 1);
                    else
                        break;

                reachableNodes.add(furthestNodeToTheLeft);
                log.info("Added furthest reachable node to the left: {}", furthestNodeToTheLeft);
            }
        }

        return reachableNodes;
    }

    public List<Point2D> getClosestReachableNodes(Point2D point) {

        Point2D roundedPoint = new Point2D(Math.round(point.getX()), Math.round(point.getY()));

        if (isPointOnNode(roundedPoint))
            return adjacencyList.get(roundedPoint);
        else {

            List<Point2D> reachableNodes = new ArrayList<>();

            Set<Point2D> xAlignedNeighbors = adjacencyList
                    .keySet()
                    .stream()
                    .filter(point2D -> point2D.getX() == roundedPoint.getX())
                    .collect(Collectors.toSet());

            if (xAlignedNeighbors.size() == 0)
                log.debug("No x-aligned neighbors!");
            if (log.isTraceEnabled())
                xAlignedNeighbors.forEach(log::trace);

            if (xAlignedNeighbors.size() > 1) {

                xAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getY() > roundedPoint.getY())
                        .min(Comparator.comparing(Point2D::getY))
                        .ifPresent(reachableNodes::add);

                xAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getY() < roundedPoint.getY())
                        .max(Comparator.comparing(Point2D::getY))
                        .ifPresent(reachableNodes::add);
            }

            Set<Point2D> yAlignedNeighbors = adjacencyList
                    .keySet()
                    .stream()
                    .filter(point2D -> point2D.getY() == roundedPoint.getY())
                    .collect(Collectors.toSet());

            if (yAlignedNeighbors.size() == 0)
                log.debug("No y-aligned neighbors!");
            if (log.isTraceEnabled())
                yAlignedNeighbors.forEach(log::trace);

            if (yAlignedNeighbors.size() > 1) {

                yAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getX() > roundedPoint.getX())
                        .min(Comparator.comparing(Point2D::getX))
                        .ifPresent(reachableNodes::add);

                yAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getX() < roundedPoint.getX())
                        .max(Comparator.comparing(Point2D::getX))
                        .ifPresent(reachableNodes::add);
            }

            return reachableNodes;
        }
    }
}
