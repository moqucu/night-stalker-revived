package model;

import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static model.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Animal extends AnimatedSprite implements Updatable {

    private Random random = new Random();

    Animal(Coordinates currentCoordinates) {
        super(currentCoordinates);
    }

    private void randomlyPickDirection(List<Direction> availableDirections) {

        direction = availableDirections.get(random.nextInt(availableDirections.size()));
    }

    @Override
    protected boolean intersects(Sprite sprite) {

        if (isFriendlyObject(sprite))
            return false;
        else
            return sprite.getBoundary().intersects(this.getBoundary());
    }

    private boolean isFriendlyObject(Sprite sprite) {

        boolean friendlyObject;

        if (sprite instanceof ShadowSprite)
            friendlyObject = (((ShadowSprite) sprite).getShadowCaster() instanceof Bat
                    || ((ShadowSprite) sprite).getShadowCaster() instanceof Spider);
        else
            friendlyObject = (sprite instanceof Bat || sprite instanceof Spider);

        return friendlyObject;
    }

    private List<Direction> determineAvailableDirections(List<Sprite> sprites, double deltaTime) {

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

    private void removeDirectionOppositeToCurrentDirectionIfPossible(List<Direction> availableDirections) {

        if (availableDirections.size() == 1)
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

    private void moveToCurrentDirection(Coordinates coordinates, Direction direction, double deltaTime) {

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

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        List<Direction> availableDirections = determineAvailableDirections(sprites, deltaTime);
        removeDirectionOppositeToCurrentDirectionIfPossible(availableDirections);
        randomlyPickDirection(availableDirections);
        moveToCurrentDirection(getCurrentCoordinates(), direction, deltaTime);
    }
}
