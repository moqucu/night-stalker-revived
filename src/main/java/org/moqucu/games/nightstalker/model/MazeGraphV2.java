package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;

/**
 * Represents on ordered graph of relative positions. Also offers service methods for determining the closest
 * and furthest point, given a starting point and a direction.
 */
@Log4j2
public class MazeGraphV2 {

    public static class UnrecognizedDirectionException extends RuntimeException {

        UnrecognizedDirectionException(String message) {

            super(message);
        }
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AdjacencyList {

        private RelativePosition node;
        private Set<RelativePosition> adjacentNodes;
    }

    @Getter
    private final Map<RelativePosition, Set<RelativePosition>> adjacencyList = new HashMap<>();

    private final Comparator<RelativePosition> ascByHorizontalAxis = Comparator.comparing(RelativePosition::getX);

    private final Comparator<RelativePosition> ascByVerticalAxis = Comparator.comparing(RelativePosition::getY);

    public MazeGraphV2() {
    }

    private void addDirectedEdge(RelativePosition node, RelativePosition adjacentNode) {

        final Set<RelativePosition> adjacentNodes = adjacencyList.getOrDefault(node, new HashSet<>());
        adjacentNodes.add(adjacentNode);
        adjacencyList.put(node, adjacentNodes);
    }

    private void addEge(RelativePosition node, RelativePosition adjacentNode) {

        addDirectedEdge(node, adjacentNode);
        addDirectedEdge(adjacentNode, node);
    }

    @SneakyThrows
    public void loadFromJson(InputStream adjacencyListJsonArray) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<AdjacencyList> adjacencyLists
                = objectMapper.readValue(adjacencyListJsonArray, new TypeReference<List<AdjacencyList>>() {
        });

        adjacencyLists.forEach(adjacencyListItem -> adjacencyListItem.getAdjacentNodes().forEach(node ->
                addEge(
                        new RelativePosition(adjacencyListItem.node.getX(), adjacencyListItem.node.getY()),
                        new RelativePosition(node.getX(), node.getY())
                )));
    }

    public void empty() {

        adjacencyList.values().forEach(Set::clear);
        adjacencyList.clear();
    }

    private Predicate<RelativePosition> getPredicateForAnyNodeToTheLeft(RelativePosition node) {

        Predicate<RelativePosition> onSameHorizontalAxis = relativePosition -> relativePosition.getY() == node.getY();
        Predicate<RelativePosition> toTheLeft = relativePosition -> relativePosition.getX() < node.getX();

        return onSameHorizontalAxis.and(toTheLeft);
    }

    private RelativePosition getClosestReachableNodeToTheLeft(RelativePosition node) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeToTheLeft(node))
                .max(ascByHorizontalAxis)
                .orElse(node);
    }

    private Predicate<RelativePosition> getPredicateForAnyNodeToTheRight(RelativePosition node) {

        Predicate<RelativePosition> onSameHorizontalAxis = relativePosition -> relativePosition.getY() == node.getY();
        Predicate<RelativePosition> toTheRight = relativePosition -> relativePosition.getX() > node.getX();

        return onSameHorizontalAxis.and(toTheRight);
    }

    private RelativePosition getClosestReachableNodeToTheRight(RelativePosition node) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeToTheRight(node))
                .min(ascByHorizontalAxis)
                .orElse(node);
    }

    private Predicate<RelativePosition> getPredicateForAnyNodeAbove(RelativePosition node) {

        Predicate<RelativePosition> onSameVerticalAxis = relativePosition -> relativePosition.getX() == node.getX();
        Predicate<RelativePosition> aboveTheGivenPoint = relativePosition -> relativePosition.getY() < node.getY();

        return onSameVerticalAxis.and(aboveTheGivenPoint);
    }

    private RelativePosition getClosestReachableNodeAbove(RelativePosition node) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeAbove(node))
                .max(ascByVerticalAxis)
                .orElse(node);
    }

    private Predicate<RelativePosition> getPredicateForAnyNodeBelow(RelativePosition node) {

        Predicate<RelativePosition> onSameVerticalAxis = relativePosition -> relativePosition.getX() == node.getX();
        Predicate<RelativePosition> belowTheGivenPoint = relativePosition -> relativePosition.getY() > node.getY();

        return onSameVerticalAxis.and(belowTheGivenPoint);
    }

    private RelativePosition getClosestReachableNodeBelow(RelativePosition node) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(getPredicateForAnyNodeBelow(node))
                .min(ascByVerticalAxis)
                .orElse(node);
    }

    public RelativePosition getClosestReachableNode(RelativePosition node, Direction direction) {

        switch (direction) {

            case Left:
                return getClosestReachableNodeToTheLeft(node);
            case Right:
                return getClosestReachableNodeToTheRight(node);
            case Up:
                return getClosestReachableNodeAbove(node);
            case Down:
                return getClosestReachableNodeBelow(node);
            default:
                throw new UnrecognizedDirectionException(
                        MessageFormat.format("Direction {0} is not a valid input for this method!", direction)
                );
        }
    }
}
