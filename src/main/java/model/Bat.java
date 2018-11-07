package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bat extends AnimatedObject implements Updatable, Renderable {

    private Location location = new Location();

    public Bat() {

        frames.add(new Image("images/Bat 1 - 2.png"));
        frames.add(new Image("images/Bat 1 - 3.png"));
        frames.add(new Image("images/Bat 1 - 4.png"));
        frames.add(new Image("images/Bat 1 - 5.png"));
        frames.add(new Image("images/Bat 1 - 6.png"));
        frames.add(new Image("images/Bat 1 - 5.png"));
        frames.add(new Image("images/Bat 1 - 4.png"));
        frames.add(new Image("images/Bat 1 - 3.png"));

        location.setX(5);
        location.setY(1);

        duration = 0.1;
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {

        gc.clearRect(32 * location.getX(), 32 * location.getY(), 32, 32);
        gc.drawImage(
                getFrame(interpolation),
                32 * location.getX(),
                32 * location.getY());
    }

    @Override
    public void update(Set<KeyCode> input) {

        if (input.size() > 0 && input.contains(KeyCode.RIGHT))
            location.setX(location.getX()+1);

    }
}
