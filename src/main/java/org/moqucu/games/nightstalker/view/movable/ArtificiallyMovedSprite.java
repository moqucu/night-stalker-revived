package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.Random;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
abstract class ArtificiallyMovedSprite extends MovableSprite {

    private MazeGraph enemyMazeGraph;

    private Point2D previousNode = null;
    private Point2D nextNode = null;

    private Random random = new Random();

    @SneakyThrows
    ArtificiallyMovedSprite() {

        super();
    }

    void computeNextMoveAnimationBasedOnRandomDirection(EventHandler<ActionEvent> finishedEventHandler) {

        Point2D currentNode = getCurrentNode();

        List<Point2D> adjacentNodes = getAdjacentNodes(currentNode);

        if (previousNode != null && adjacentNodes.size() > 1)
            adjacentNodes.remove(previousNode);
        previousNode = currentNode;

        nextNode = adjacentNodes.get(random.nextInt(adjacentNodes.size()));

        moveAnimation = calculateTranslateTransition(currentNode, nextNode);
        moveAnimation.setOnFinished(finishedEventHandler);
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

    @SneakyThrows
    protected MazeGraph getMazeGraph() {

        if (enemyMazeGraph == null)
            enemyMazeGraph = new MazeGraph(
                    (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph.json")
                            .getInputStream())
            );

        return enemyMazeGraph;
    }
}
