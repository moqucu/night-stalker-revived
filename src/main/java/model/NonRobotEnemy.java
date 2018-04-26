package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

abstract class NonRobotEnemy extends Enemy {

    public abstract void paralyze();

}
