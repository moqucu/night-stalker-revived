package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSprite;
import org.moqucu.games.nightstalker.view.object.WeaponBulletSprite;
import org.moqucu.games.nightstalker.view.object.WeaponSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

public class WeaponSpriteTest {

    private final WeaponSprite weaponSprite = new WeaponSprite();

    @Test
    public void weaponSpriteIsAnAnimatedSprite() {

        assertThat(weaponSprite, isA(AnimatedSprite.class));
    }

    @Test
    public void modelIsAWeapon() {

        assertThat(weaponSprite.getModel(), isA(Weapon.class));
    }

    @Test
    public void hasAWeaponBulletSprite() {

        assertThat(weaponSprite, hasProperty("weaponBulletSprite"));
    }

    @Test
    public void weaponBulletSpriteOfTheSameType() {

        final WeaponBulletSprite aWeaponBulletSprite = new WeaponBulletSprite();
        weaponSprite.setWeaponBulletSprite(aWeaponBulletSprite);

        assertThat(weaponSprite.getWeaponBulletSprite(), isA(WeaponBulletSprite.class));
    }

    @Test
    public void settingWeaponBulletSpriteAlsoSetsBulletModelInWeaponModel() {

        final WeaponBulletSprite aWeaponBulletSprite = new WeaponBulletSprite();
        weaponSprite.setWeaponBulletSprite(aWeaponBulletSprite);

        assertThat(((Weapon)weaponSprite.getModel()).getBullet(), is(aWeaponBulletSprite.getModel()));
    }
}
