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
public class NightStalker extends AnimatedSprite implements Updatable {

    public NightStalker() {

        super(Coordinates.builder().x(9 * WIDTH).y(5 * HEIGHT - HEIGHT / 2).build());

        setInitialImage(new Image("images/NightStalker 1 - 1.png"));
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

            gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

    }
}