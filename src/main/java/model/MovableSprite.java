package model;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class MovableSprite extends Sprite {

    protected Direction direction = Right;

    @Singular
    protected Map<Direction, List<Image>> frames = new HashMap<>();

    MovableSprite(Coordinates currentCoordinates) {

        super(currentCoordinates);

        frames.put(Up, new ArrayList<>());
        frames.put(Right, new ArrayList<>());
        frames.put(Down, new ArrayList<>());
        frames.put(Left, new ArrayList<>());
    }

    Image getFrame(double deltaTime) {

        int index = (int) ((deltaTime % (frames.get(direction).size() * frameDuration)) / frameDuration);
        return frames.get(direction).get(index);
    }

    @Override
    protected boolean intersects(Sprite sprite) {

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

    List<Sprite> createShadowSpritePerMovableDirection(double deltaTime) {

        long pixelMoveRate = determinePixelMoveRate(deltaTime);
        List<Sprite> shadowSprites = new ArrayList<>();

        frames.keySet().forEach(
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
            Coordinates coordinates,
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

    void moveToCurrentDirection(Coordinates coordinates, Direction direction, double deltaTime) {

        switch (direction) {

            case Right:
                coordinates.setX(getCurrentCoordinates().getX() + determinePixelMoveRate(deltaTime));
                break;
            case Down:
                coordinates.setY(getCurrentCoordinates().getY() + determinePixelMoveRate(deltaTime));
                break;
            case Left:
                coordinates.setX(getCurrentCoordinates().getX() - determinePixelMoveRate(deltaTime));
                break;
            case Up:
                coordinates.setY(getCurrentCoordinates().getY() - determinePixelMoveRate(deltaTime));
                break;
        }
    }
}
