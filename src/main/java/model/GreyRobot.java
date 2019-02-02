package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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

    @Override
    public void update(
            double deltaTimeSinceStart,
            double deltaTime,
            Set<KeyCode> input,
            List<Sprite> nearbyObjects
    ) {

        log.info("Direction before update: {}", this.getDirection());
        log.info("Coordinates before update: {}", this.getCurrentCoordinates());
        log.info("Nearby objects:");
        nearbyObjects.forEach(log::info);
        List<Direction> availableDirections = determineAvailableDirections(nearbyObjects, deltaTime);
        log.info("Available direction:");
        availableDirections.forEach(log::info);
        super.update(deltaTimeSinceStart, deltaTime, input, nearbyObjects);
        log.info("Direction after update: {}", this.getDirection());
        log.info("Coordinates after update: {}", this.getCurrentCoordinates());
    }
}