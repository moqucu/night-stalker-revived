package org.moqucu.games.nightstalker.model.object;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.AnimatedObject;

import java.util.List;

@Getter
public class Weapon extends AnimatedObject {

    private final List<AbsolutePosition> possibleWeaponLocations = List.of(
            new AbsolutePosition(9.0 * 32, 6.0 * 32),
            new AbsolutePosition(17.0 * 32, 3.0 * 32),
            new AbsolutePosition(18.0 * 32, 10.0 * 32),
            new AbsolutePosition(9.0 * 32, 3.0 * 32),
            new AbsolutePosition(3.0 * 32, 9.0 * 32)
    );

    public Weapon() {

        super();
        setImageMapFileName("/images/weapon.png");
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(1);
        setFrameRate(2);
    }
}
