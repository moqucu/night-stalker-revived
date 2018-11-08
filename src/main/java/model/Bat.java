package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bat extends AnimatedSprite implements Updatable {

    public Bat() {
        super(Position.builder().horizontal(5).vertical(1).build());

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

        gc.clearRect(
                getCurrentCoordinates().getX() * WIDTH,
                getCurrentCoordinates().getY() * HEIGHT,
                WIDTH,
                HEIGHT
        );
        gc.drawImage(getFrame(interpolation),  getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public void update(Set<KeyCode> input) {

        if (input.size() > 0 && input.contains(KeyCode.RIGHT))
            getCurrentCoordinates().setX(getCurrentCoordinates().getX() + 4);
    }
}
