package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static model.Bat.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends AnimatedSprite implements Updatable {

    public NightStalker() {

        super(Coordinates.builder().x(9 * WIDTH).y(5 * HEIGHT - HEIGHT / 2).build());

        setInitialImage(new Image("images/NightStalker 1 - 1.png"));
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {

            gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public void update(Set<KeyCode> input, List<Sprite> sprites) {

    }
}
