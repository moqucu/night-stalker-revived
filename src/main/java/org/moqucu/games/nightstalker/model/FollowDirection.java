package org.moqucu.games.nightstalker.model;

import java.util.*;

public class FollowDirection implements MazeAlgorithmImpl {

    /**
     * The algorithm should be:
     * If on node and direction is possible, follow the closest reachable position.
     * Otherwise, search on orthogonal direction in both directions for next node(s) and
     * ask them if direction is possible from there. If yes, pick shortest or random.
     *
     * @param absMazeGraph       the absolute maze graph required by the algorithm
     * @param absPosAndDirection the current absolute position and intended direction
     * @return the next absolute position, either already in the direction or a potential better jump point
     */
    @Override
    public AbsPosAndDirection getNextAbsPos(AbsMazeGraph absMazeGraph, AbsPosAndDirection absPosAndDirection) {

        // Attempt naive approach: try to get somewhere directly
        final AbsPosAndDirection closestReachablePosition = new AbsPosAndDirection(
                absMazeGraph.getClosestReachablePosition(
                        absPosAndDirection.getAbsolutePosition(),
                        absPosAndDirection.getDirection()
                ),
                absPosAndDirection.getDirection()
        );

        // If calculated position is not starting position, then let's go there
        if (!absPosAndDirection.equals(closestReachablePosition))
            return closestReachablePosition;

            // If calculated position becomes starting position, we need to look for alternatives
        else {

            // Determine alternative directions
            List<Direction> alternativeDirections = switch (absPosAndDirection.getDirection()) {
                case Up, Down -> Arrays.asList(Direction.Right, Direction.Left);
                case Left, Right -> Arrays.asList(Direction.Up, Direction.Down);
                default -> throw new OuterRing.UnacceptableDirectionException(
                        String.format("%s is an unacceptable direction!", absPosAndDirection.getDirection())
                );
            };

            // Attempt to get alternative jump points based on alternative directions
            final List<AbsPosAndDirection> alternativeJumpPoints = alternativeDirections
                    .stream()
                    .map(alternativeDirection -> new AbsPosAndDirection(
                                    absMazeGraph.getClosestReachablePosition(
                                            absPosAndDirection.getAbsolutePosition(),
                                            alternativeDirection
                                    ),
                                    alternativeDirection
                            )
                    ).toList();

            // Find those jump points that support the original direction
            final List<AbsPosAndDirection> supportiveAlternativeDirections = alternativeJumpPoints
                    .stream()
                    .filter(alternativeJumpPoint -> {
                                final AbsPosAndDirection potentiallyNextPointPoint = new AbsPosAndDirection(
                                        absMazeGraph.getClosestReachablePosition(
                                                alternativeJumpPoint.getAbsolutePosition(),
                                                absPosAndDirection.getDirection()
                                        ),
                                        absPosAndDirection.getDirection()
                                );
                                return !alternativeJumpPoint
                                        .getAbsolutePosition()
                                        .equals(potentiallyNextPointPoint.getAbsolutePosition());
                            }
                    ).toList();


            final Map<Double, AbsPosAndDirection> distanceMap = new HashMap<>();
            for (AbsPosAndDirection element : supportiveAlternativeDirections) {

                final double distance = absPosAndDirection.getAbsolutePosition().distanceTo(element.getAbsolutePosition());
                distanceMap.put(distance, element);
            }

            final Optional<Double> minDistance = distanceMap.keySet().stream().min(Double::compare);

            if (minDistance.isEmpty())
                return absPosAndDirection;
            else
                return distanceMap.get(minDistance.get());
        }
    }
}
