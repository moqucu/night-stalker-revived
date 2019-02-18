package org.moqucu.games.nightstalker.view.movable;

import javafx.geometry.Point2D;
import org.moqucu.games.nightstalker.data.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.Updatable;
import org.moqucu.games.nightstalker.view.immovable.Gun;

import java.util.List;
import java.util.Set;
import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends MovableSprite implements Updatable {

    private Gun gun = null;

    public NightStalker() {

        super(new Point2D(9 * WIDTH, 5 * HEIGHT - HEIGHT / 2));

        setInitialImage(new Image(translate("images/NightStalker_1_1.png")));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            frames2.get(directions[i]).add(new Image(translate("images/NightStalker_1_1.png")));
        }

        setVelocity(70);

        frameDuration = 0.1;
    }

    @Override
    protected boolean isFriendlyObject(Sprite sprite) {

        return false;
    }

    @Override
    protected List<Direction> determineAvailableDirections(List<Sprite> sprites, double deltaTime) {

        List<Direction> availableDirections = super.determineAvailableDirections(sprites, deltaTime);

        if (getCurrentCoordinates().getX() / WIDTH == 9 && (getCurrentCoordinates().getY() / HEIGHT == 4
                || getCurrentCoordinates().getY() / HEIGHT == 3))
            availableDirections.add(Direction.Up);

        if (getCurrentCoordinates().getX() / WIDTH == 9 && getCurrentCoordinates().getY() / HEIGHT == 3)
            availableDirections.add(Direction.Down);

        return availableDirections;
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        List<Direction> availableDirections = determineAvailableDirections(sprites, deltaTime);

        sprites.forEach(sprite -> {

            if (gun == null && sprite instanceof Gun && sprite.getBoundary().intersects(this.getBoundary()))
                gun = ((Gun)sprite).pickUp();
        });

        input.forEach(inputSignal -> {

            switch (inputSignal) {
                case UP:

                    if (availableDirections.contains(Direction.Up))
                        moveToCurrentDirection(getCurrentCoordinates(), Direction.Up, deltaTime);
                    break;
                case RIGHT:

                    if (availableDirections.contains(Direction.Right))
                        moveToCurrentDirection(getCurrentCoordinates(), Direction.Right, deltaTime);
                    break;
                case DOWN:

                    if (availableDirections.contains(Direction.Down))
                        moveToCurrentDirection(getCurrentCoordinates(), Direction.Down, deltaTime);
                    break;
                case LEFT:

                    if (availableDirections.contains(Direction.Left))
                        moveToCurrentDirection(getCurrentCoordinates(), Direction.Left, deltaTime);
                    break;
                case SPACE:

                    if (gun != null)

                        try {
                            gun.fire();
                        } catch(Gun.NoMoreRoundsException e) {
                            gun = gun.drop();
                        }

                    break;
            }
        });
    }
}