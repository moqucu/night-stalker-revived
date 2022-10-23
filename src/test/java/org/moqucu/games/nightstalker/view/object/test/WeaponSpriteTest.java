package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSpriteV2;
import org.moqucu.games.nightstalker.view.object.WeaponSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

public class WeaponSpriteTest {

    private final WeaponSprite weaponSprite = new WeaponSprite();

    @Test
    public void weaponSpriteIsAnAnimatedSprite() {

        assertThat(weaponSprite, isA(AnimatedSpriteV2.class));
    }

    @Test
    public void modelIsAWeapon() {

        assertThat(weaponSprite.getModel(), isA(Weapon.class));
    }
}
