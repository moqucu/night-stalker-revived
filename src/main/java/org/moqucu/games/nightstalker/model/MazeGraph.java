package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Point2D;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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

    // ToDo: needs to be tested and can potentially be generalized through reflection with "X" and "Y" parameter
    public List<Point2D> getReachableNodes(Point2D point) {

        if (isPointOnNode(point))
            return adjacencyList.get(point);
        else {

            List<Point2D> reachableNodes = new ArrayList<>();

            Set<Point2D> xAlignedNeighbors = adjacencyList
                    .keySet()
                    .stream()
                    .filter(point2D -> point2D.getX() == point.getX())
                    .collect(Collectors.toSet());

            if (xAlignedNeighbors.size() > 1) {

                Point2D directNeighborToTheRight = xAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getX() > point.getX())
                        .min(Comparator.comparing(Point2D::getX))
                        .orElse(null);

                Point2D directNeighborToTheLeft = xAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getX() < point.getX())
                        .max(Comparator.comparing(Point2D::getX))
                        .orElse(null);

                if (directNeighborToTheRight != null && directNeighborToTheLeft != null)
                    reachableNodes.addAll(Set.of(directNeighborToTheRight, directNeighborToTheLeft));
            }

            Set <Point2D> yAlignedNeighbors = adjacencyList
                    .keySet()
                    .stream()
                    .filter(point2D -> point2D.getY() == point.getY())
                    .collect(Collectors.toSet());

            if (yAlignedNeighbors.size() > 1) {

                Point2D directNeighborBelow = yAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getY() > point.getY())
                        .min(Comparator.comparing(Point2D::getY))
                        .orElse(null);

                Point2D directNeighborAbove = yAlignedNeighbors
                        .stream()
                        .filter(point2D -> point2D.getY() < point.getY())
                        .max(Comparator.comparing(Point2D::getY))
                        .orElse(null);

                if (directNeighborBelow != null && directNeighborAbove != null)
                    reachableNodes.addAll(Set.of(directNeighborBelow, directNeighborAbove));
            }

            return reachableNodes;
        }
    }
}
