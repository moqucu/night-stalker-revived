package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.object.Weapon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class WeaponTest {

    private final Weapon weapon = new Weapon();

    @Test
    public void weaponIsAnAnimatedObject() {

        assertThat(weapon, isA(AnimatedObject.class));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(weapon.getImageMapFileName(), is("/images/weapon.png"));
    }

    @Test
    public void lowerAnimationIndexIsOne() {

        assertThat(weapon.getLowerAnimationIndex(), is(0));
    }

    @Test
    public void upperAnimationIndexIsFive() {

        assertThat(weapon.getUpperAnimationIndex(), is(1));
    }

    @Test
    public void frameRateIsTen() {

        assertThat(weapon.getFrameRate(), is(2));
    }

}
