package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.Maze;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.Updatable;
import org.moqucu.games.nightstalker.view.movable.NightStalker;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class Gun extends AnimatedSprite implements Updatable {

    public class NoMoreRoundsException extends Exception {
    }

    private boolean awake = false;

    private boolean pickedUp = false;

    private double sleepTime;

    private byte rounds = 6;

    private AudioClip pickUpGunSound = new AudioClip(Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/pickupgun.wav").toString());

    private AudioClip shootSound = new AudioClip(Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/shoot.wav").toString());

    private int[][] randomGunPositions = {{9, 6}, {17, 3}, {18, 10}, {9, 3}, {3, 9}};

    private int randomIndex = (new Random()).nextInt(4);

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private NightStalker nightStalker = null;

    public Gun(double sleepTime) {

        super(new Point2D(3 * WIDTH, 9 * HEIGHT));

        setCurrentCoordinates(
                new Point2D(
                        randomGunPositions[randomIndex][0] * WIDTH,
                        randomGunPositions[randomIndex][1] * HEIGHT
                )
        );

        setInitialImage(new Image(translate("images/Empty Sprite.png")));

        frames.add(new Image(translate("images/Weapon Full Size.png")));
        frames.add(new Image(translate("images/Empty Sprite.png")));

        setVelocity(35);

        frameDuration = 0.4;

        this.sleepTime = sleepTime;
    }

    @Override
    public boolean intersects(Sprite sprite) {

        return false;
    }

    @Override
    public void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> sprites) {

        if (!awake && !(deltaTimeSinceStart < sleepTime)) {

            awake = true;
            pickUpGunSound.setVolume(0.1f);
            pickUpGunSound.play();
        }
    }

    public void fire() throws NoMoreRoundsException {

        if (rounds > 0) {

            rounds--;
            shootSound.setVolume(0.1f);
            shootSound.play();
        } else
            throw new NoMoreRoundsException();
    }

    public Gun pickUp() {

        rounds = 6;
        pickedUp = true;
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();

        return this;
    }

    public Gun drop() {

        pickedUp = false;
        // awake = false;
        randomIndex = (new Random()).nextInt(4);
        setCurrentCoordinates(
                new Point2D(
                        randomGunPositions[randomIndex][0] * WIDTH,
                        randomGunPositions[randomIndex][1] * HEIGHT
                )
        );

        return null;
    }
}
