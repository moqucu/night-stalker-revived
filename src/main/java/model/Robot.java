package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
class Robot extends Enemy implements ArmedObject {

    enum Level {
        GreyRobot,
        BlueRobot,
        WhiteRobot,
        BlackRobotLevelOne,
        BlackRobotLevelTwo,
        InvisibleRobot
    }

    private Integer shields;

    private Level level;

    public void wasHit() {

    }

    public void findTarget() {

    }

    public void fireWeapon() {

    }

    public void determineNextMove() {

    }

    public void move() {

    }

    public void changeDirection() {

    }

    public void respawn() {

    }
}
