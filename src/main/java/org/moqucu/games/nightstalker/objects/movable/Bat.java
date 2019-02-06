package org.moqucu.games.nightstalker.objects.movable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.data.Direction;
import org.moqucu.games.nightstalker.objects.Sprite;

import java.util.*;

@Data
@Log4j2
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends ArtificiallyMovedSprite {

    private boolean awake = false;

    private double sleepTime;

    public Bat(int initialXCoordinate, int initialYCoordinate, double sleepTime) {

        super(Coordinates.builder().x(initialXCoordinate * WIDTH - WIDTH / 2).y(initialYCoordinate * HEIGHT).build());

        setInitialImage(new Image("images/Bat 1 - 1.png"));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            frames.get(directions[i]).add(new Image("images/Bat 1 - 2.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 3.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 4.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 5.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 6.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 5.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 4.png"));
            frames.get(directions[i]).add(new Image("images/Bat 1 - 3.png"));
        }

        setVelocity(35);

        frameDuration = 0.1;

        this.sleepTime = sleepTime;
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        if (awake)
            gc.drawImage(getFrame(deltaTime), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
        else
            gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        if (deltaTimeSinceStart < sleepTime)
            return;
        else
            awake = true;

        log.info("Direction before update: {}", this.getDirection());
        log.info("Coordinates before update: {}", this.getCurrentCoordinates());
        log.info("Nearby org.moqucu.games.nightstalker.objects:");
        sprites.forEach(log::info);
        List<Direction> availableDirections = determineAvailableDirections(sprites, deltaTime);
        log.info("Available direction:");
        availableDirections.forEach(log::info);


        super.update(deltaTimeSinceStart, deltaTime, input, sprites);

        log.info("Direction after update: {}", this.getDirection());
        log.info("Coordinates after update: {}", this.getCurrentCoordinates());

    }
}
