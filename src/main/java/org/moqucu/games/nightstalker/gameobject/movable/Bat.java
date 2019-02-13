package org.moqucu.games.nightstalker.gameobject.movable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.data.Direction;
import org.moqucu.games.nightstalker.gameobject.Sprite;

import java.util.*;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bat extends ArtificiallyMovedSprite {

    private boolean awake = false;

    private double sleepTime;

    public Bat(int initialXCoordinate, int initialYCoordinate, double sleepTime) {

        super(new Point2D(initialXCoordinate * WIDTH - WIDTH / 2, initialYCoordinate * HEIGHT));

        setInitialImage(new Image(translate("images/Bat 1 - 1.png")));

        Direction[] directions = Direction.values();
        for (int i = 0; i < Direction.values().length; i++) {

            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 2.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 3.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 4.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 5.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 6.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 5.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 4.png")));
            frames.get(directions[i]).add(new Image(translate("images/Bat 1 - 3.png")));
        }

        setVelocity(35);

        frameDuration = 0.1;

        this.sleepTime = sleepTime;
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        if (deltaTimeSinceStart < sleepTime)
            return;
        else
            awake = true;

        super.update(deltaTimeSinceStart, deltaTime, input, sprites);
    }
}
