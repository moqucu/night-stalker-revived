package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.view.object.BulletSprite;
import org.moqucu.games.nightstalker.view.object.WeaponBulletSprite;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

@ExtendWith(ApplicationExtension.class)
public class WeaponBulletSpriteTest {

    private final WeaponBulletSprite weaponBulletRectangle = new WeaponBulletSprite();

    @Test
    public void bulletRectangleIsASprite() {

        assertThat(weaponBulletRectangle, isA(BulletSprite.class));
    }
}
