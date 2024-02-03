package org.moqucu.games.nightstalker.model.hero;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.object.Weapon;

@Getter
@Log4j2
public class NightStalker extends MovableObject implements Resettable {

    public static class NoWeaponsException extends RuntimeException {

        public NoWeaponsException() {

            super("Night stalker is not in possession of any weapons!");
        }
    }

    private boolean running;

    @Setter
    private boolean leftPressed;

    @Setter
    private boolean rightPressed;

    @Setter
    private boolean upPressed;

    @Setter
    private boolean downPressed;

    private Weapon weapon;

    public NightStalker() {

        this.addPropertyChangeListener(
                evt -> {

                    if (evt.getPropertyName().equals("running")) {
                        if (evt.getNewValue().equals(true)) {
                            switch (getDirection()) {
                                case Up, Down -> {
                                    setLowerAnimationIndex(1);
                                    setUpperAnimationIndex(2);
                                }
                                case Left -> {
                                    setLowerAnimationIndex(3);
                                    setUpperAnimationIndex(10);
                                }
                                case Right -> {
                                    setLowerAnimationIndex(11);
                                    setUpperAnimationIndex(18);
                                }
                                case OnTop, Undefined -> {
                                    setLowerAnimationIndex(0);
                                    setUpperAnimationIndex(0);
                                }
                            }
                            setAnimated(true);
                            setInMotion(true);
                        } else
                            resetAnimationIndicesAndOtherProperties();
                    }
                }
        );
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        setMazeGraphFileName("/json/maze-graph-night-stalker.json");
        setImageMapFileName("/images/night-stalker.png");
        setFrameRate(10);
        setVelocity(30);
        reset();
    }

    private void resetAnimationIndicesAndOtherProperties() {

        setInitialImageIndex(0);
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(0);
        setAnimated(false);
        setInMotion(false);
    }

    public void setRunning(boolean running) {

        final boolean oldRunning = this.running;
        this.running = running;
        propertyChangeSupport.firePropertyChange(
                "running",
                oldRunning,
                running
        );
    }

    public void stop() {

        setRunning(false);
    }

    public void run(Direction direction) {

        setDirection(direction);
        setRunning(true);
    }

    @Override
    public void reset() {

        setXPosition(288);
        setYPosition(144);
        setDirection(Direction.Up);
        if (!running)
            resetAnimationIndicesAndOtherProperties();
        stop();
     }

    @Override
    public void elapseTime(double milliseconds) {

        if (isLeftPressed())
            run(Direction.Left);
        else if (isRightPressed())
            run(Direction.Right);
        else if (isUpPressed())
            run(Direction.Up);
        else if (isDownPressed())
            run(Direction.Down);
        else
            stop();
        super.elapseTime(milliseconds);
    }

    private void setWeapon(Weapon weapon) {

        final Weapon oldWeapon = this.weapon;
        this.weapon = weapon;
        propertyChangeSupport.firePropertyChange(
                "weapon",
                oldWeapon,
                weapon
        );
    }

    public void pickUpWeapon(Weapon weapon) {

        this.setWeapon(weapon);
        getWeapon().pickUp();
    }

    public void throwAwayWeapon() {

        weapon.drop();
        this.setWeapon(null);
    }

    public void fireWeapon() {

        if (weapon == null)
            throw new NoWeaponsException();
        try {
            final AbsolutePosition targetPosition = getAbsMazeGraph().getFurthestReachablePosition(
                    getAbsolutePosition(),
                    getDirection()
            );
            getWeapon().fireRound(this, getDirection(), getAbsolutePosition(), targetPosition);
        } catch (Weapon.WeaponFiredEmptyException weaponFiredEmptyException) {
            throwAwayWeapon();
        }
    }

    @Override
    public void collisionOccurredWith(Collidable anotherCollidable) {

        if (anotherCollidable instanceof Weapon && (getWeapon() == null || !getWeapon().equals(anotherCollidable)))
            pickUpWeapon((Weapon)anotherCollidable);
    }

    @Override
    public boolean canChangePosition() {
        return true;
    }
}
