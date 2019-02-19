package org.moqucu.games.nightstalker.view.movable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.view.Sprite;

import java.util.List;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class GreyRobot extends ArtificiallyMovedSprite {

    public GreyRobot() {

        this(1, 10);
    }

    private GreyRobot(int initialXCoordinate, int initialYCoordinate) {

        super(new Point2D(initialXCoordinate * WIDTH, initialYCoordinate * HEIGHT));

        setInitialImage(new Image(translate("images/Grey_Robot_1.png")));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

                frames2.get(directions[i]).add(new Image(translate("images/Grey_Robot_1.png")));
                frames2.get(directions[i]).add(new Image(translate("images/Grey_Robot_2.png")));
        }

        setVelocity(35);

        frameDuration = 0.1;
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
        log.info("Nearby org.moqucu.games.nightstalker.objects:");
        nearbyObjects.forEach(log::info);
        List<Direction> availableDirections = determineAvailableDirections(nearbyObjects, deltaTime);
        log.info("Available direction:");
        availableDirections.forEach(log::info);
        super.update(deltaTimeSinceStart, deltaTime, input, nearbyObjects);
        log.info("Direction after update: {}", this.getDirection());
        log.info("Coordinates after update: {}", this.getCurrentCoordinates());
    }
}
