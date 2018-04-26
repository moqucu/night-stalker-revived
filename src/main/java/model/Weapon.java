package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
class Weapon extends MazeObject {

    enum State {
        NotVisible,
        NotPickedUp,
        PickedUp
    }

    private Integer bullets;
    private State state;

    public void drawWeapon() {

    }

    /**
     * Remove items from the items array
     * Make bullet move
     */
    public void fire() {

    }

}
