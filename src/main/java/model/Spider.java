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
public class Spider extends Animal {

    public Spider(int initialXCoordinate, int initialYCoordinate) {

        super(Coordinates.builder().x(initialXCoordinate * WIDTH - WIDTH / 2).y(initialYCoordinate * HEIGHT).build());

        setInitialImage(new Image("images/Spider - Vertical 2.png"));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            if (i % 2 == 0) {

                frames.get(directions[i]).add(new Image("images/Spider - Vertical 2.png"));
                frames.get(directions[i]).add(new Image("images/Spider - Vertical 3.png"));

            } else {

                frames.get(directions[i]).add(new Image("images/Spider - Horizontal 1.png"));
                frames.get(directions[i]).add(new Image("images/Spider - Horizontal 2.png"));
            }
        }

        setVelocity(35);

        frameDuration = 0.1;
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

            gc.drawImage(getFrame(deltaTime), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }
}
