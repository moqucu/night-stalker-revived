package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.object.Weapon;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void hasPossibleWeaponLocationsProperty() {

        assertThat(weapon, hasProperty("possibleWeaponLocations"));
    }

    @Test
    public void possibleWeaponLocationsPropertyOfTypeAbsolutePointList() {

        assertThat(weapon.getPossibleWeaponLocations(), isA(List.class));
    }

    @Test
    public void assertPossibleWeaponLocations() {

        assertThat(
                weapon.getPossibleWeaponLocations(),
                hasItems(
                        new AbsolutePosition(9.0 * 32, 6.0 * 32),
                        new AbsolutePosition(17.0 * 32, 3.0 * 32),
                        new AbsolutePosition(18.0 * 32, 10.0 * 32),
                        new AbsolutePosition(9.0 * 32, 3.0 * 32),
                        new AbsolutePosition(3.0 * 32, 9.0 * 32)
                )
        );
    }
}
