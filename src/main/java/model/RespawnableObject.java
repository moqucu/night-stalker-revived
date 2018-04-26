package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
abstract class RespawnableObject extends MazeObject {

    enum Direction {
        North,
        South,
        West,
        East
    }

    private Direction currentDirection;

    private Integer lives;

    public abstract void move();

    public abstract void wasHit();

    public abstract void changeDirection();

    public abstract void respawn();
}
