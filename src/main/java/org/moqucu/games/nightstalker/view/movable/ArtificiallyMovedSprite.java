package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import lombok.*;
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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final DoubleProperty velocity = new SimpleDoubleProperty(35d);

    @SneakyThrows
    ArtificiallyMovedSprite() {

        super();
    }

    Animation prepareAnimationForMovingSpriteRandomlyAlongMazeGraph() {

        Point2D currentNode = getCurrentNode();
        log.debug("current coordinates: {}, {}", getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
        log.debug("Adjacency list: {} {}", getMazeGraph(), getMazeGraph().getAdjacencyList().get(currentNode));

        List<Point2D> adjacentNodes = getAdjacentNodes(currentNode);

        if (previousNode != null && adjacentNodes.size() > 1)
            adjacentNodes.remove(previousNode);
        previousNode = currentNode;

        nextNode = adjacentNodes.get(random.nextInt(adjacentNodes.size()));

        translateTransition = calculateTranslateTransition(currentNode, nextNode);

        return translateTransition;
    }

    TranslateTransition calculateTranslateTransition(Point2D currentNode, Point2D nextNode) {

        double deltaX = nextNode.getX()-currentNode.getX();
        double deltaY = nextNode.getY()-currentNode.getY();

        Duration duration;
        if (deltaX != 0)
            duration = Duration.millis(Math.abs(deltaX)/getVelocity() * 1000);
        else
            duration = Duration.millis(Math.abs(deltaY)/getVelocity() * 1000);

        TranslateTransition translateTransition = new TranslateTransition(duration, this);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setByX(deltaX);
        translateTransition.setByY(deltaY);
        translateTransition.setCycleCount(1);

        return translateTransition;
    }

    ArrayList<Point2D> getAdjacentNodes(Point2D currentNode) {

        return new ArrayList<>(List.copyOf(getMazeGraph().getAdjacencyList().get(currentNode)));
    }

    Point2D getCurrentNode() {

        return new Point2D(getBoundsInParent().getMinX(), getBoundsInParent().getMinY());
    }

    protected abstract MazeGraph getMazeGraph();

    @SuppressWarnings("WeakerAccess")
    public double getVelocity() {

        return velocity.get();
    }

    @SuppressWarnings("WeakerAccess")
    public void setVelocity(double velocity) {

        this.velocity.set(velocity);
    }

    @SuppressWarnings("unused")
    public DoubleProperty velocityProperty() {

        return velocity;
    }

}
