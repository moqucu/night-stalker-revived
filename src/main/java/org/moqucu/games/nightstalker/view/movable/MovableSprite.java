package org.moqucu.games.nightstalker.view.movable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.ShadowSprite;
import org.moqucu.games.nightstalker.view.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.moqucu.games.nightstalker.model.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class MovableSprite extends AnimatedSprite {

    protected Direction direction = Right;

    @Singular
    protected Map<Direction, List<Image>> frames2 = new HashMap<>();

    MovableSprite(Point2D currentCoordinates) {

        super(currentCoordinates);

        frames2.put(Up, new ArrayList<>());
        frames2.put(Right, new ArrayList<>());
        frames2.put(Down, new ArrayList<>());
        frames2.put(Left, new ArrayList<>());
    }

    //ToDo: convert to viewport based function
   /* Image getFrame(double deltaTime) {

        int index = (int) ((deltaTime % (frames2.get(direction).size() * frameDuration)) / frameDuration);
        return frames2.get(direction).get(index);
    }*/

    @Override
    public boolean intersects(Sprite sprite) {

        if (isFriendlyObject(sprite))
            return false;
        else
            return sprite.getBoundary().intersects(this.getBoundary());
    }

    protected abstract boolean isFriendlyObject(Sprite sprite);

    protected List<Direction> determineAvailableDirections(List<Sprite> sprites, double deltaTime) {

        List<Direction> availableDirections = new ArrayList<>();
        availableDirections.add(Up);
        availableDirections.add(Right);
        availableDirections.add(Down);
        availableDirections.add(Left);

        return availableDirections
                .stream()
                .filter(availableDirection -> !checkDirectionForCollision(
                        getCurrentCoordinates(),
                        availableDirection,
                        sprites,
                        deltaTime
                ))
                .collect(Collectors.toList());
    }

    public List<Sprite> createShadowSpritePerMovableDirection(double deltaTime) {

        long pixelMoveRate = determinePixelMoveRate(deltaTime);
        List<Sprite> shadowSprites = new ArrayList<>();

        frames2.keySet().forEach(
                key -> {
                    switch (key) {
                        case Up:
                            shadowSprites.add(
                                    new ShadowSprite(
                                            this,
                                            getCurrentCoordinates().getX(),
                                            getCurrentCoordinates().getY() - pixelMoveRate
                                    )
                            );
                            break;
                        case Down:
                            shadowSprites.add(
                                    new ShadowSprite(
                                            this,
                                            getCurrentCoordinates().getX(),
                                            getCurrentCoordinates().getY() + pixelMoveRate
                                    )
                            );
                            break;
                        case Left:
                            shadowSprites.add(
                                    new ShadowSprite(
                                            this,
                                            getCurrentCoordinates().getX() - pixelMoveRate,
                                            getCurrentCoordinates().getY()
                                    )
                            );
                            break;
                        case Right:
                            shadowSprites.add(
                                    new ShadowSprite(
                                            this,
                                            getCurrentCoordinates().getX() + pixelMoveRate,
                                            getCurrentCoordinates().getY()
                                    )
                            );
                            break;
                    }
                }
        );

        return shadowSprites;
    }

    private boolean checkDirectionForCollision(
            Point2D coordinates,
            Direction direction,
            List<Sprite> sprites,
            double deltaTime) {

        ShadowSprite shadowSprite
                = new ShadowSprite(this, coordinates.getX(), coordinates.getY());

        moveToCurrentDirection(shadowSprite.getCurrentCoordinates(), direction, deltaTime);

        return sprites
                .stream()
                .anyMatch(sprite -> sprite.intersects(shadowSprite) & !sprite.equals(this));
    }

    void moveToCurrentDirection(Point2D coordinates, Direction direction, double deltaTime) {

        switch (direction) {

            case Right:
                coordinates.add(getCurrentCoordinates().getX() + determinePixelMoveRate(deltaTime), 0);
                break;
            case Down:
                coordinates.add(0, getCurrentCoordinates().getY() + determinePixelMoveRate(deltaTime));
                break;
            case Left:
                coordinates.add(getCurrentCoordinates().getX() - determinePixelMoveRate(deltaTime), 0);
                break;
            case Up:
                coordinates.add(0, getCurrentCoordinates().getY() - determinePixelMoveRate(deltaTime));
                break;
        }
    }
}
