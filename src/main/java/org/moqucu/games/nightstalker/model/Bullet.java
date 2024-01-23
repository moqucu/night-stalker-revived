package org.moqucu.games.nightstalker.model;

import lombok.Getter;

@Getter
public class Bullet extends DisplayableObject implements TimeListener {

    public static class FiringDirectionNotSupportedException extends RuntimeException {

        FiringDirectionNotSupportedException(Direction direction) {

            super("Firing direction " + direction.toString() + " not supported!");
        }
    }

    public static final double VELOCITY_PER_SECOND = 16;

    private AbsolutePosition absoluteTargetPosition = getAbsolutePosition();

    private GameObject source = this;

    private boolean fired;

    private Direction direction = Direction.Undefined;

    public Bullet() {

        super();
        propertyChangeSupport.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("fired") && evt.getNewValue().equals(true))
                        setObjectVisible(true);
                    else if (evt.getPropertyName().equals("fired") && evt.getNewValue().equals(false)) {
                        setObjectVisible(false);
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

    private void setAbsoluteTargetPosition(AbsolutePosition absoluteTargetPosition) {

        final AbsolutePosition oldAbsoluteTargetPosition = this.absoluteTargetPosition;
        this.absoluteTargetPosition = absoluteTargetPosition;
        propertyChangeSupport.firePropertyChange(
                "absoluteTargetPosition",
                oldAbsoluteTargetPosition,
                absoluteTargetPosition
        );
    }

    public void fire(GameObject source, Direction direction, AbsolutePosition start, AbsolutePosition target) {

        switch (direction) {
            case OnTop, Undefined -> throw new FiringDirectionNotSupportedException(direction);
            case Left -> setAbsoluteTargetPosition(
                    new AbsolutePosition(
                            target.getX(),
                            target.getY() + DisplayableObject.HEIGHT / 2
                    )
            );
            case Right -> setAbsoluteTargetPosition(
                    new AbsolutePosition(
                            target.getX() + DisplayableObject.WIDTH,
                            target.getY() + DisplayableObject.HEIGHT / 2
                    )
            );
            case Up -> setAbsoluteTargetPosition(
                    new AbsolutePosition(
                            target.getX() + DisplayableObject.WIDTH / 2,
                            target.getY()
                    )
            );
            case Down -> setAbsoluteTargetPosition(
                    new AbsolutePosition(
                            target.getX() + DisplayableObject.WIDTH / 2,
                            target.getY() + DisplayableObject.HEIGHT
                    )
            );
        }
        setXPosition(start.getX() + DisplayableObject.WIDTH / 2);
        setYPosition(start.getY() + DisplayableObject.HEIGHT / 2);
        setSource(source);
        setDirection(direction);
        setFired(true);
    }

    @Override
    public void collisionOccurredWith(Collidable anotherCollidable) {

        if (anotherCollidable != this && anotherCollidable != source)
            setFired(false);
    }

    @Override
    public void elapseTime(double milliseconds) {

        final double range = milliseconds / 1000 * VELOCITY_PER_SECOND;

        if (fired) {
            switch (direction) {
                case Left -> setXPosition(getXPosition() - range);
                case Right -> setXPosition(getXPosition() + range);
                case Up -> setYPosition(getXPosition() - range);
                case Down -> setYPosition(getXPosition() + range);
            }
        }
    }
}
