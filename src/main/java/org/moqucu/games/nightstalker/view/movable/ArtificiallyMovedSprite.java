package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Random;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
abstract class ArtificiallyMovedSprite extends MovableSprite {

    private Random random = new Random();
    private Animation translateTransition;

    @SneakyThrows
    ArtificiallyMovedSprite() {

        super();
    }

    Animation prepareAnimationForMovingSpriteRandomlyAlongMazeGraph() {

        Point2D currentNode = getCurrentNode();
        log.debug("current coordinates: {}, {}", getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
        log.debug("Adjacency list: {} {}", getMazeGraph(), getMazeGraph().getAdjacencyList().get(currentNode));

        List<Point2D> adjacentNodes = getAdjacentNodes(currentNode);

        if (getPreviousNode() != null && adjacentNodes.size() > 1)
            adjacentNodes.remove(getPreviousNode());
        setPreviousNode(currentNode);

        setNextNode(adjacentNodes.get(random.nextInt(adjacentNodes.size())));

        translateTransition = calculateTranslateTransition(currentNode, getNextNode());

        return translateTransition;
    }

    private TranslateTransition calculateTranslateTransition(Point2D currentNode, Point2D nextNode) {

        log.debug("Calculating transition between {} and {}", currentNode, nextNode);

        TranslateTransition translateTransition = new TranslateTransition(
                calculateDurationAtConfiguredVelocity(currentNode, nextNode),
                this
        );
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setByX(deltaX(currentNode, nextNode));
        translateTransition.setByY(deltaY(currentNode, nextNode));
        translateTransition.setCycleCount(1);

        return translateTransition;
    }
}
