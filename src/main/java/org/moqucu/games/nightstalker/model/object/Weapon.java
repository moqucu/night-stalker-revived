package org.moqucu.games.nightstalker.model.object;

import lombok.Getter;
import lombok.Setter;
import org.moqucu.games.nightstalker.model.*;

import java.util.List;
import java.util.Random;

public class Weapon extends AnimatedObject implements Resettable {

    public static class WeaponFiredEmptyException extends RuntimeException {

        public WeaponFiredEmptyException() {

            super();
        }
    }

    @Getter
    private int rounds;

    private final Random random = new Random();

    @Getter
    private final List<AbsolutePosition> possibleWeaponLocations = List.of(
            new AbsolutePosition(9.0 * 32, 6.0 * 32),
            new AbsolutePosition(17.0 * 32, 3.0 * 32),
            new AbsolutePosition(18.0 * 32, 10.0 * 32),
            new AbsolutePosition(9.0 * 32, 3.0 * 32),
            new AbsolutePosition(3.0 * 32, 9.0 * 32)
    );

    @Setter
    private Bullet bullet;

    private void setRounds(int rounds) {

        final int oldRounds = this.rounds;
        this.rounds = rounds;
        propertyChangeSupport.firePropertyChange(
                "rounds",
                oldRounds,
                rounds
        );
        if (rounds == 0) {

            setRounds(6);
            throw new WeaponFiredEmptyException();
        }
    }

    private void randomizeAbsolutePosition() {

        final AbsolutePosition nextAbsPos = possibleWeaponLocations.get(random.nextInt(possibleWeaponLocations.size()));
        setXPosition(nextAbsPos.getX());
        setYPosition(nextAbsPos.getY());
    }

    public Weapon() {

        super();
        setImageMapFileName("/images/weapon.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(2);
        drop();
    }

    public void fireRound(GameObject source, Direction direction, AbsolutePosition start, AbsolutePosition target) {

        getBullet().fire(source, direction, start, target);
        setRounds(getRounds() - 1);
    }

    public void pickUp() {

        setObjectVisible(false);
        setAnimated(false);
    }

    public void drop() {

        setRounds(6);
        randomizeAbsolutePosition();
        setObjectVisible(true);
        setAnimated(true);
    }

    @Override
    public boolean canChangePosition() {

        return true;
    }

    @Override
    public void reset() {

        drop();
    }

    public Bullet getBullet() {

        if (this.bullet == null)
            this.bullet = new Bullet();

        return this.bullet;
    }
}
