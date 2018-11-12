package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

import static model.Bat.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bat extends AnimatedSprite implements Updatable {

    public static final int PIXEL = 1;

    enum Direction {

        Up, Right, Down, Left
    }

    private boolean awake = false;

    private double sleepTime;

    private Direction direction = Right;

    private Random random = new Random();

    public Bat(int initialXCoordinate, int initialYCoordinate, double sleepTime) {

        super(Coordinates.builder().x(initialXCoordinate * WIDTH - WIDTH / 2).y(initialYCoordinate * HEIGHT).build());

        setInitialImage(new Image("images/Bat 1 - 1.png"));

        frames.add(new Image("images/Bat 1 - 2.png"));
        frames.add(new Image("images/Bat 1 - 3.png"));
        frames.add(new Image("images/Bat 1 - 4.png"));
        frames.add(new Image("images/Bat 1 - 5.png"));
        frames.add(new Image("images/Bat 1 - 6.png"));
        frames.add(new Image("images/Bat 1 - 5.png"));
        frames.add(new Image("images/Bat 1 - 4.png"));
        frames.add(new Image("images/Bat 1 - 3.png"));

        frameDuration = 0.1;

        this.sleepTime = sleepTime;
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {

        if (interpolation < sleepTime)
            gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
        else {
            awake = true;
            gc.drawImage(getFrame(interpolation), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
        }
    }

    @Override
    public void update(Set<KeyCode> input, List<Sprite> sprites) {

        if (!awake)
            return;

        List<Direction> availableDirections = determineAvailableDirections(sprites);
        removeDirectionOppositeToCurrentDirectionIfPossible(availableDirections);
        randomlyPickDirection(availableDirections);
        advanceCoordinatesByDirection(getCurrentCoordinates(), direction);
    }

    private void randomlyPickDirection(List<Direction> availableDirections) {

        direction = availableDirections.get(random.nextInt(availableDirections.size()));
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

    private boolean checkDirectionForCollision(Coordinates coordinates, Direction direction, List<Sprite> sprites) {

        ShadowSprite shadowSprite
                = new ShadowSprite(this, coordinates.getX(), coordinates.getY());

        advanceCoordinatesByDirection(shadowSprite.getCurrentCoordinates(), direction);

        return sprites
                .stream()
                .anyMatch(sprite -> sprite.intersects(shadowSprite) & !sprite.equals(this));
    }

    private void advanceCoordinatesByDirection(Coordinates coordinates, Direction direction) {

        switch (direction) {

            case Right:
                coordinates.setX(getCurrentCoordinates().getX() + PIXEL);
                break;
            case Down:
                coordinates.setY(getCurrentCoordinates().getY() + PIXEL);
                break;
            case Left:
                coordinates.setX(getCurrentCoordinates().getX() - PIXEL);
                break;
            case Up:
                coordinates.setY(getCurrentCoordinates().getY() - PIXEL);
                break;
        }
    }

    private List<Direction> determineAvailableDirections(List<Sprite> sprites) {

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
                        sprites
                ))
                .collect(Collectors.toList());
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
            friendlyObject = ((ShadowSprite)sprite).getShadowCaster() instanceof Bat;
        else
            friendlyObject = sprite instanceof Bat;

        return friendlyObject;
    }
}
