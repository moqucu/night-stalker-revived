package org.moqucu.games.nightstalker.gameobject.movable;

import javafx.geometry.Point2D;
import org.moqucu.games.nightstalker.gameobject.Updatable;
import org.moqucu.games.nightstalker.data.Direction;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.gameobject.*;
import org.moqucu.games.nightstalker.gameobject.immovable.Gun;
import org.moqucu.games.nightstalker.gameobject.immovable.ShadowSprite;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.moqucu.games.nightstalker.data.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ArtificiallyMovedSprite extends MovableSprite implements Updatable {

    private Random random = new Random();

    ArtificiallyMovedSprite(Point2D currentCoordinates) {

        super(currentCoordinates);
    }

    private void randomlyPickDirection(List<Direction> availableDirections) {

            direction = availableDirections.get(random.nextInt(availableDirections.size()));
    }

    protected boolean isFriendlyObject(Sprite sprite) {

        boolean friendlyObject;

        if (sprite instanceof ShadowSprite)
            friendlyObject = (((ShadowSprite) sprite).getShadowCaster() instanceof Bat
                    || ((ShadowSprite) sprite).getShadowCaster() instanceof Spider
                    || ((ShadowSprite) sprite).getShadowCaster() instanceof GreyRobot
                    || ((ShadowSprite) sprite).getShadowCaster() instanceof Gun);
        else
            friendlyObject = (
                    sprite instanceof Bat
                            || sprite instanceof Spider
                            || sprite instanceof GreyRobot
                            || sprite instanceof Gun

            );

        return friendlyObject;
    }

    private void removeDirectionOppositeToCurrentDirectionIfPossible(List<Direction> availableDirections) {

        if (availableDirections.size() <= 1)
            return;

        switch (direction) {

            case Right:
                availableDirections.remove(Left);
                break;
            case Down:
                availableDirections.remove(Up);
                break;
            case Left:
                availableDirections.remove(Right);
                break;
            case Up:
                availableDirections.remove(Down);
                break;
        }
    }

    @Override
    public void update(
            double deltaTimeSinceStart,
            double deltaTime,
            Set<KeyCode> input,
            List<Sprite> nearbyObjects
    ) {

        List<Direction> availableDirections = determineAvailableDirections(nearbyObjects, deltaTime);
        removeDirectionOppositeToCurrentDirectionIfPossible(availableDirections);
        randomlyPickDirection(availableDirections);
        moveToCurrentDirection(getCurrentCoordinates(), direction, deltaTime);
    }
}
