package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.view.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ArtificiallyMovedSprite extends AnimatedSprite {

    private Random random = new Random();
    private Animation translateTransition;
    private Point2D previousNode = null;
    private Point2D nextNode = null;

    @SneakyThrows
    ArtificiallyMovedSprite() {

        super();
    }

    Animation prepareAnimationForMovingSpriteRandomlyAlongMazeGraph() {

        Point2D currentNode = new Point2D(getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
        log.info("current coordinates: {}, {}", getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
        log.info("Adjacency list: {} {}", getMazeGraph(), getMazeGraph().getAdjacencyList().get(currentNode));

        List<Point2D> adjacentNodes = new ArrayList<>(List.copyOf(getMazeGraph().getAdjacencyList().get(currentNode)));

        if (previousNode != null && adjacentNodes.size() > 1)
            adjacentNodes.remove(previousNode);
        previousNode = currentNode;

        nextNode = adjacentNodes.get(random.nextInt(adjacentNodes.size()));

        double deltaX = nextNode.getX()-currentNode.getX();
        double deltaY = nextNode.getY()-currentNode.getY();

        Duration duration;
        if (deltaX != 0)
            duration = Duration.millis(Math.abs(deltaX)/getVelocity() * 1000);
        else
            duration = Duration.millis(Math.abs(deltaY)/getVelocity() * 1000);

        translateTransition = new TranslateTransition(duration, this);
        ((TranslateTransition) translateTransition).setInterpolator(Interpolator.LINEAR);

        ((TranslateTransition) translateTransition).setByX(deltaX);
        ((TranslateTransition) translateTransition).setByY(deltaY);
        translateTransition.setCycleCount(1);

        return translateTransition;
    }

    protected abstract MazeGraph getMazeGraph();
}
