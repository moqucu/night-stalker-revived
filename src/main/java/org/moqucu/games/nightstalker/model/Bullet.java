package org.moqucu.games.nightstalker.model;

import lombok.Getter;

@Getter
public class Bullet extends DisplayableObject implements TimeListener {

    public static class FiringDirectionNotSupportedException extends RuntimeException {

        FiringDirectionNotSupportedException(Direction direction) {

            super("Firing direction " + direction.toString() + " not supported!");
        }
    }

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

    public void fire(GameObject source, Direction direction, AbsolutePosition start, AbsolutePosition target) {

        switch (direction) {
            case OnTop, Undefined -> throw new FiringDirectionNotSupportedException(direction);
            case Left -> {
//       todo         setXPosition(start + Visibility);
            }
        }
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

    }
}
