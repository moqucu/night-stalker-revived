package org.moqucu.games.nightstalker.view.object;

import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSprite;

public class WeaponSprite extends AnimatedSprite {

    public WeaponSprite() {

        super(new Weapon());
        getModel().setAnimated(true);
    }
}
