package org.moqucu.games.nightstalker.sprite;

import javafx.animation.Animation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.MazeGraph;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class MovableSprite extends AnimatedSprite {

    protected Animation moveAnimation;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final DoubleProperty velocity = new SimpleDoubleProperty(35d);

    @SneakyThrows
    MovableSprite() {

        super();
    }

    protected void moveMeFromStart() {

        moveAnimation.playFromStart();
    }

    protected void stopMovingMe() {

        moveAnimation.stop();
    }

    Duration calculateDurationAtConfiguredVelocity(Point2D currentNode, Point2D nextNode) {

        if (deltaX(currentNode, nextNode) != 0)
            return Duration.millis(Math.abs(deltaX(currentNode, nextNode))/getVelocity() * 1000);
        else
            return Duration.millis(Math.abs(deltaY(currentNode, nextNode))/getVelocity() * 1000);
    }

    double deltaX(Point2D currentNode, Point2D nextNode) {

        return nextNode.subtract(currentNode).getX();
    }

    double deltaY(Point2D currentNode, Point2D nextNode) {

        return nextNode.subtract(currentNode).getY();
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
