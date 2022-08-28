package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        List<AdjacencyListElement> adjacencyLists
                = objectMapper.readValue(adjacencyListJsonArray, new TypeReference<List<AdjacencyListElement>>() {
        });

        adjacencyLists.forEach(adjacencyListItem -> adjacencyListItem.getAdjacentNodes().forEach(node ->
                addEge(
                        new RelativePosition(adjacencyListItem.getNode().getX(), adjacencyListItem.getNode().getY()),
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

    private boolean isNodeOnNode(RelativePosition node) {

        return adjacencyList.containsKey(node);
    }

    private boolean noNodesThatMatchCondition(RelativePosition node, Predicate<RelativePosition> condition) {

        return adjacencyList.get(node)
                .stream()
                .filter(condition)
                .findAny()
                .isEmpty();
    }

    private List<RelativePosition> returnAllNodesThatMatchConditionAsOrderedList(
            Predicate<RelativePosition> condition,
            Comparator<RelativePosition> comparator
    ) {

        return adjacencyList
                .keySet()
                .stream()
                .filter(condition)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private void logAllNodesIndividuallyWhenInTraceMode(Direction direction, List<RelativePosition> nodes) {

        if (log.isTraceEnabled()) {

            log.trace("Nodes {} from here...", direction);
            nodes.forEach(log::trace);
        }
    }

    private RelativePosition findAndReturnFurthestNodesInAscOrder(
            Direction direction,
            RelativePosition node,
            List<RelativePosition> nodes
    ) {

        RelativePosition furthestNode = nodes.size() > 0 ? nodes.get(0) : node;

        for (int i = 0; i + 1 < nodes.size(); i++)

            if (adjacencyList.get(nodes.get(i)).contains(nodes.get(i + 1)))
                furthestNode = nodes.get(i + 1);
            else
                break;

        log.debug("Returning furthest reachable node in direction {}: {}", direction, furthestNode);

        return furthestNode;
    }

    private RelativePosition findAndReturnFurthestNodesInDescOrder(
            Direction direction,
            RelativePosition node,
            List<RelativePosition> nodes
    ) {

        RelativePosition furthestNode = nodes.size() > 0 ? nodes.get(nodes.size() - 1) : node;

        for (int i = nodes.size() - 1; i > 0; i--)

            if (adjacencyList.get(nodes.get(i)).contains(nodes.get(i - 1)))
                furthestNode = nodes.get(i - 1);
            else
                break;

        log.debug("Returning furthest reachable node in direction {}: {}", direction, furthestNode);

        return furthestNode;
    }

    private RelativePosition returnClosestNodeInDirectionThatMatchesCondition(
            RelativePosition node,
            Direction direction,
            Predicate<RelativePosition> condition,
            Comparator<RelativePosition> comparator
    ) {

        if (isNodeOnNode(node) && noNodesThatMatchCondition(node, condition))
            return node;

        List<RelativePosition> sortedListOfNodesThatMatchCondition
                = returnAllNodesThatMatchConditionAsOrderedList(condition, comparator);

        logAllNodesIndividuallyWhenInTraceMode(direction, sortedListOfNodesThatMatchCondition);

        switch (direction) {
            case Down:
            case Right:
                return findAndReturnFurthestNodesInAscOrder(direction, node, sortedListOfNodesThatMatchCondition);
            default: // Up & Left
                return findAndReturnFurthestNodesInDescOrder(direction, node, sortedListOfNodesThatMatchCondition);
        }
    }

    public RelativePosition getFurthestReachableNode(RelativePosition node, Direction direction) {

        Predicate<RelativePosition> predicate;
        Comparator<RelativePosition> comparator;

        switch (direction) {
            case Left:
                predicate = getPredicateForAnyNodeToTheLeft(node);
                comparator = ascByHorizontalAxis;
                break;
            case Up:
                predicate = getPredicateForAnyNodeAbove(node);
                comparator = ascByVerticalAxis;
                break;
            case Right:
                predicate = getPredicateForAnyNodeToTheRight(node);
                comparator = ascByHorizontalAxis;
                break;
            case Down:
                predicate = getPredicateForAnyNodeBelow(node);
                comparator = ascByVerticalAxis;
                break;
            default:
                return node;
        }

        return returnClosestNodeInDirectionThatMatchesCondition(
                node,
                direction,
                predicate,
                comparator
        );
    }

}
