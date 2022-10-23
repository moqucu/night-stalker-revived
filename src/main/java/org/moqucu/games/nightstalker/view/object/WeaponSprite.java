package org.moqucu.games.nightstalker.view.object;

import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSpriteV2;

public class WeaponSprite extends AnimatedSpriteV2 {

    public WeaponSprite() {

        super(new Weapon());
        getModel().setAnimated(true);
    }
}
