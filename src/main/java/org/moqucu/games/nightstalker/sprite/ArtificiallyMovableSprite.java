package org.moqucu.games.nightstalker.sprite;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ArtificiallyMovableSprite extends MovableSprite {

    private MazeGraph enemyMazeGraph;

    private Point2D previousNode = null;
    private Point2D nextNode = null;

    private Random random = new Random();

    @SneakyThrows
    protected ArtificiallyMovableSprite() {

        super();
        moveAnimation = new TranslateTransition();
        moveAnimation.setCycleCount(1);
        getMoveAnimation().setNode(this);
        getMoveAnimation().setInterpolator(Interpolator.LINEAR);
    }

    protected void setOnFinished(EventHandler<ActionEvent> finishedActionEventHandler) {

        moveAnimation.setOnFinished(finishedActionEventHandler);
    }

    protected void computeNextMoveAnimationBasedOnRandomDirection() {

        Point2D currentNode = getCurrentNode();

        List<Point2D> adjacentNodes = getAdjacentNodes(currentNode);

        if (previousNode != null && adjacentNodes.size() > 1)
            adjacentNodes.remove(previousNode);
        previousNode = currentNode;

        nextNode = adjacentNodes.get(random.nextInt(adjacentNodes.size()));

        updateMoveAnimationWithNewLocationTranslation(currentNode, nextNode);
    }

    private ArrayList<Point2D> getAdjacentNodes(Point2D currentNode) {

        return new ArrayList<>(List.copyOf(getMazeGraph().getAdjacencyList().get(currentNode)));
    }

    private void updateMoveAnimationWithNewLocationTranslation(Point2D startPoint, Point2D endPoint) {

        log.debug("Calculating transition between {} and {}", startPoint, endPoint);

        getMoveAnimation().setDuration(calculateDurationAtConfiguredVelocity(startPoint, endPoint));
        getMoveAnimation().setByX(deltaX(startPoint, endPoint));
        getMoveAnimation().setByY(deltaY(startPoint, endPoint));
    }

    @SneakyThrows
    protected MazeGraph getMazeGraph() {

        if (enemyMazeGraph == null)
            enemyMazeGraph = new MazeGraph(
                    (new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph-enemy.json")
                            .getInputStream())
            );

        return enemyMazeGraph;
    }

    public TranslateTransition getMoveAnimation() {

        return (TranslateTransition) moveAnimation;
    }
}
