package org.moqucu.games.nightstalker.view.movable;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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

    private Duration calculateDuration(Point2D currentNode, Point2D nextNode) {

        if (deltaX(currentNode, nextNode) != 0)
            return Duration.millis(Math.abs(deltaX(currentNode, nextNode))/getVelocity() * 1000);
        else
            return Duration.millis(Math.abs(deltaY(currentNode, nextNode))/getVelocity() * 1000);
    }

    private double deltaX(Point2D currentNode, Point2D nextNode) {

        return nextNode.subtract(currentNode).getX();
    }

    private double deltaY(Point2D currentNode, Point2D nextNode) {

        return nextNode.subtract(currentNode).getY();
    }

    private TranslateTransition calculateTranslateTransition(Point2D currentNode, Point2D nextNode) {

        log.debug("Calculating transition between {} and {}", currentNode, nextNode);

        TranslateTransition translateTransition = new TranslateTransition(
                calculateDuration(currentNode, nextNode),
                this
        );
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setByX(deltaX(currentNode, nextNode));
        translateTransition.setByY(deltaY(currentNode, nextNode));
        translateTransition.setCycleCount(1);

        return translateTransition;
    }

    private ArrayList<Point2D> getAdjacentNodes(Point2D currentNode) {

        return new ArrayList<>(List.copyOf(getMazeGraph().getAdjacencyList().get(currentNode)));
    }

    PathTransition calculatePathTransition(Point2D point, KeyCode direction, double maxOffset) {

        log.info("Starting point: {}", point);

        Path path = new Path();
        //log.info("Computed moveTo shape: {}", moveTo);


        Point2D furthestReachableNode = getMazeGraph().getFurthestReachableNode(point, direction);
        MoveTo moveTo = new MoveTo(furthestReachableNode.getX(), furthestReachableNode.getY());
        moveTo.setAbsolute(true);
        path.getElements().add(moveTo);

        log.info("Initial value for furthest reachable point: {}", furthestReachableNode);
        // LineTo lineTo = new LineTo(deltaX(point, furthestReachableNode), deltaY(point, furthestReachableNode));
       //  LineTo lineTo = new LineTo(furthestReachableNode.getX(), furthestReachableNode.getY());
        // lineTo.setAbsolute(true);

        // log.info("Computed lineTo shape: {}", lineTo);
        // path.getElements().add(lineTo);

        Duration duration = calculateDuration(point, furthestReachableNode);
        log.info("Calculated duration: {}", duration);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setPath(path);
        pathTransition.setDuration(duration);
        pathTransition.setCycleCount(1);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);

        return pathTransition;
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
