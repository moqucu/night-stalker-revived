package org.moqucu.games.nightstalker.model.object;

import org.moqucu.games.nightstalker.model.AnimatedObject;

public class Weapon extends AnimatedObject {

    public Weapon() {

        super();
        setImageMapFileName("/images/weapon.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(2);
    }
}
