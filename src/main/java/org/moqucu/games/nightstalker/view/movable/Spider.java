package org.moqucu.games.nightstalker.view.movable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.data.Direction;
import org.moqucu.games.nightstalker.view.Sprite;

import java.util.List;

import static org.moqucu.games.nightstalker.data.Direction.*;
import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class Spider extends ArtificiallyMovedSprite {

    public Spider(int initialXCoordinate, int initialYCoordinate) {

        super(new Point2D(initialXCoordinate * WIDTH, initialYCoordinate * HEIGHT));

        setInitialImage(new Image(translate("images/Spider_Vertical_2.png")));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            if (i % 2 == 0) {

                frames.get(directions[i]).add(new Image(translate("images/Spider_Vertical_2.png")));
                frames.get(directions[i]).add(new Image(translate("images/Spider_Vertical_3.png")));

            } else {

                frames.get(directions[i]).add(new Image(translate("images/Spider_Horizontal_1.png")));
                frames.get(directions[i]).add(new Image(translate("images/Spider_Horizontal_2.png")));
            }
        }

        setVelocity(35);

        frameDuration = 0.1;
    }

    @Override
    protected List<Direction> determineAvailableDirections(List<Sprite> sprites, double deltaTime) {

        List<Direction> availableDirections = super.determineAvailableDirections(sprites, deltaTime);

        Point2D currentCoordinates = getCurrentCoordinates();

        if (currentCoordinates.getX() / WIDTH == 3 && currentCoordinates.getY() / HEIGHT == 5)
            availableDirections.remove(Right);

        if (currentCoordinates.getX() / WIDTH == 3 && currentCoordinates.getY() / HEIGHT == 8)
            availableDirections.remove(Down);

        return availableDirections;
    }
}
