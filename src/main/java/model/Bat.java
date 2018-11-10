package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

import static model.Bat.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bat extends AnimatedSprite implements Updatable {

    enum Direction {
        Up, Right, Down, Left;

        private static final List<Direction> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction randomDirection()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    private boolean awake = false;

    private Direction direction = Right;

    public Bat() {
        super(Coordinates.builder().x(17 * WIDTH - WIDTH / 2).y(3 * HEIGHT).build());

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
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {

        if (interpolation < 5.0)
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

        boolean collision = checkDirectionForCollision(getCurrentCoordinates(), direction, sprites);

        if (collision) {

            Direction randomDirection = Direction.randomDirection();
            while (checkDirectionForCollision(getCurrentCoordinates(), randomDirection, sprites)) {
                randomDirection = Direction.randomDirection();
            }
            direction = randomDirection;

        }

        advanceCoordinatesByDirection(getCurrentCoordinates(), direction);
    }

    private boolean checkDirectionForCollision(Coordinates coordinates, Direction direction, List<Sprite> sprites) {

        TransparentSprite transparentSprite
                = new TransparentSprite(coordinates.getX(), coordinates.getY());

        advanceCoordinatesByDirection(transparentSprite.getCurrentCoordinates(), direction);

        return sprites
                .stream()
                .anyMatch(sprite -> sprite.intersects(transparentSprite) & !sprite.equals(this));
    }

    private void advanceCoordinatesByDirection(Coordinates coordinates, Direction direction) {

        switch (direction) {

            case Right:
                coordinates.setX(getCurrentCoordinates().getX() + 4);
                break;
            case Down:
                coordinates.setY(getCurrentCoordinates().getY() + 4);
                break;
            case Left:
                coordinates.setX(getCurrentCoordinates().getX() - 4);
                break;
            case Up:
                coordinates.setY(getCurrentCoordinates().getY() - 4);
                break;
        }
    }
}
