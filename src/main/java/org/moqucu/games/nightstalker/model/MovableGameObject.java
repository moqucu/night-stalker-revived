package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import lombok.Setter;
import org.moqucu.games.nightstalker.event.TimeListener;

public abstract class MovableGameObject extends GameObject implements TimeListener {

    @Getter
    @Setter
    private double velocity;

    @Getter
    private boolean inMotion = false;

    @Getter
    @Setter
    private Direction direction = Direction.Undefined;

    @Override
    public void elapseTime(long milliseconds) {

        if (isInMotion()) {

            switch (getDirection()) {

                case Up:
                    getPosition().addToY(-1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Down:
                    getPosition().addToY(1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Left:
                    getPosition().addToX(-1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Right:
                    getPosition().addToX(1.0 * milliseconds / 1000 * getVelocity());
                    break;
            }
        }
    }

    public void setInMotion() {

        if (direction != Direction.Undefined)
            inMotion = true;
    }

    public void stop() {

        inMotion = false;
    }
}
