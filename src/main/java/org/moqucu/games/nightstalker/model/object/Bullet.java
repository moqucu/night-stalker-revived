package org.moqucu.games.nightstalker.model.object;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.*;

@Getter
public class Bullet extends DisplayableObject implements TimeListener, Resettable {

    public static class FiringDirectionNotSupportedException extends RuntimeException {

        FiringDirectionNotSupportedException(Direction direction) {

            super("Firing direction " + direction.toString() + " not supported!");
        }
    }

    public static final double VELOCITY_PER_SECOND = 64;

    private GameObject source = this;

    private boolean fired;

    private Direction direction = Direction.Undefined;

    public Bullet() {

        super();
        setImageMapFileName("/images/bullet.png");
        setInitialImageIndex(0);
        propertyChangeSupport.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("fired") && evt.getNewValue().equals(true)) {
                        setObjectVisible(true);
                        setInitialImageIndex(1);
                    }
                    else if (evt.getPropertyName().equals("fired") && evt.getNewValue().equals(false)) {
                        setObjectVisible(false);
                        setInitialImageIndex(0);
                        setSource(this);
                        setDirection(Direction.Undefined);
                    }
                }
        );
    }

    private void setFired(boolean fired) {

        final boolean oldFired = this.fired;
        this.fired = fired;
        propertyChangeSupport.firePropertyChange("fired", oldFired, fired);
    }

    private void setDirection(Direction direction) {

        final Direction oldDirection = this.direction;
        this.direction = direction;
        propertyChangeSupport.firePropertyChange("direction", oldDirection, direction);
    }

    private void setSource(GameObject source) {

        final GameObject oldSource = this.source;
        this.source = source;
        propertyChangeSupport.firePropertyChange("source", oldSource, source);
    }

    public void fire(GameObject source, Direction direction, AbsolutePosition start) {

        switch (direction) {
            case OnTop, Undefined -> throw new FiringDirectionNotSupportedException(direction);
            case Left, Right, Up, Down -> {
                setXPosition(start.getX());
                setYPosition(start.getY());
                setSource(source);
                setDirection(direction);
                setFired(true);
            }
        }
    }

    @Override
    public void collisionOccurredWith(Collidable anotherCollidable) {

        if (!(anotherCollidable instanceof Weapon) && isFired() && anotherCollidable != this && anotherCollidable != source)
            setFired(false);
    }

    @Override
    public void elapseTime(double milliseconds) {

        final double range = milliseconds / 1000 * VELOCITY_PER_SECOND;

        if (fired) {
            switch (direction) {
                case Left -> setXPosition(getXPosition() - range);
                case Right -> setXPosition(getXPosition() + range);
                case Up -> setYPosition(getYPosition() - range);
                case Down -> setYPosition(getYPosition() + range);
            }
        }
    }

    @Override
    public void reset() {

        setFired(false);
        setSource(this);
        setDirection(Direction.Undefined);
    }
}
