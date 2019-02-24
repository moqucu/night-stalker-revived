package org.moqucu.games.nightstalker.view.movable;

import org.moqucu.games.nightstalker.model.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.Updatable;
import org.moqucu.games.nightstalker.view.immovable.Weapon;

import java.util.List;
import java.util.Set;
import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class NightStalker extends MovableSprite implements Updatable {

    private Weapon weapon = null;

    public NightStalker() {

        super();
        relocate(9 * WIDTH, 5 * HEIGHT - HEIGHT / 2);

        setInitialImage(new Image(translate("images/NightStalker_1_1.png")));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            frames2.get(directions[i]).add(new Image(translate("images/NightStalker_1_1.png")));
        }

        setVelocity(70);

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

//            if (weapon == null && sprite instanceof Weapon && sprite.getBoundary().intersects(this.getBoundary()))
                /*weapon = ((Weapon)sprite).pickUp();*/
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

                    if (weapon != null)

                        try {
                            weapon.fire();
                        } catch(Weapon.NoMoreRoundsException e) {
                            /*weapon = weapon.drop();*/
                        }

                    break;
            }
        });
    }
}
