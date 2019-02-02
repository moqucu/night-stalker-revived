package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Gun extends AnimatedSprite implements Updatable {

    public class NoMoreRoundsException extends Exception {
    }

    private boolean awake = false;

    private boolean pickedUp = false;

    private double sleepTime;

    private byte rounds = 6;

    private AudioClip pickUpGunSound = new AudioClip(World.class.getResource("/sounds/pickupgun.wav").toString());

    private AudioClip shootSound = new AudioClip(World.class.getResource("/sounds/shoot.wav").toString());

    private int[][] randomGunPositions = {{9, 6}, {17, 3}, {18, 10}, {9, 3}, {3, 9}};

    private int randomIndex = (new Random()).nextInt(4);

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private NightStalker nightStalker = null;

    public Gun(double sleepTime) {

        super(Coordinates.builder().x(3 * WIDTH).y(9 * HEIGHT).build());

        setCurrentCoordinates(Coordinates.builder()
                .x(randomGunPositions[randomIndex][0] * WIDTH)
                .y(randomGunPositions[randomIndex][1] * HEIGHT)
                .build());

        setInitialImage(new Image("images/Empty Sprite.png"));

        frames.add(new Image("images/Weapon Full Size.png"));
        frames.add(new Image("images/Empty Sprite.png"));

        setVelocity(35);

        frameDuration = 0.4;

        this.sleepTime = sleepTime;
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        if (awake && !pickedUp)
            gc.drawImage(getFrame(deltaTime), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
        else
            gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    protected boolean intersects(Sprite sprite) {

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

    void fire() throws NoMoreRoundsException {

        if (rounds > 0) {

            rounds--;
            shootSound.setVolume(0.1f);
            shootSound.play();
        } else
            throw new NoMoreRoundsException();
    }

    Gun pickUp() {

        rounds = 6;
        pickedUp = true;
        pickUpGunSound.setVolume(0.1f);
        pickUpGunSound.play();

        return this;
    }

    Gun drop() {

        pickedUp = false;
        // awake = false;
        randomIndex = (new Random()).nextInt(4);
        setCurrentCoordinates(Coordinates.builder()
                .x(randomGunPositions[randomIndex][0] * WIDTH)
                .y(randomGunPositions[randomIndex][1] * HEIGHT)
                .build());

        return null;
    }
}