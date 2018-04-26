package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
abstract class Enemy extends RespawnableObject {

    public abstract void determineNextMove();
}
