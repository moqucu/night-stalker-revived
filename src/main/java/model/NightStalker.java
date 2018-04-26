package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
class NightStalker extends RespawnableObject implements ArmedObject {

    private Weapon weapon;

    private boolean inBunker;

    private boolean paralyzed;

    public void fireWeapon() {

    }

    public void move() {

    }

    public void wasHit() {

    }

    public void changeDirection() {

    }

    public void respawn() {

    }
}
