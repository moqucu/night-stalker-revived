package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends ArtificiallyMovedSprite {

    public GreyRobot(int initialXCoordinate, int initialYCoordinate) {

        super(Coordinates.builder().x(initialXCoordinate * WIDTH).y(initialYCoordinate * HEIGHT).build());

        setInitialImage(new Image("images/Grey Robot - 1.png"));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

                frames.get(directions[i]).add(new Image("images/Grey Robot - 1.png"));
                frames.get(directions[i]).add(new Image("images/Grey Robot - 2.png"));
        }

        setVelocity(35);

        frameDuration = 0.1;
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

            gc.drawImage(getFrame(deltaTime), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }
}