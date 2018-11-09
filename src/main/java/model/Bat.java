package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bat extends AnimatedSprite implements Updatable {

    private boolean awake = false;

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

        TransparentSprite transparentSprite
                = new TransparentSprite(getCurrentCoordinates().getX(), getCurrentCoordinates().getY());

        if (input.size() > 0 && input.contains(KeyCode.RIGHT))
            transparentSprite.getCurrentCoordinates().setX(getCurrentCoordinates().getX() + 4);
        if (input.size() > 0 && input.contains(KeyCode.LEFT))
            transparentSprite.getCurrentCoordinates().setX(getCurrentCoordinates().getX() - 4);
        if (input.size() > 0 && input.contains(KeyCode.UP))
            transparentSprite.getCurrentCoordinates().setY(getCurrentCoordinates().getY() - 4);
        if (input.size() > 0 && input.contains(KeyCode.DOWN))
            transparentSprite.getCurrentCoordinates().setY(getCurrentCoordinates().getY() + 4);

        boolean anyCollision = sprites
                .stream()
                .anyMatch(sprite -> sprite.intersects(transparentSprite) & !sprite.equals(this));

        if (!anyCollision) {
            setCurrentCoordinates(
                    Coordinates.builder()
                            .x(transparentSprite.getCurrentCoordinates().getX())
                            .y(transparentSprite.getCurrentCoordinates().getY())
                            .build()
            );
        }


    }
}
