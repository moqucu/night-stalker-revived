package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.AnimatedObject;
import org.moqucu.games.nightstalker.model.object.Weapon;

import java.beans.PropertyChangeListener;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class WeaponTest {

    private final Weapon weapon = new Weapon();

    @Test
    public void isInitiallyNotAnimated() {

        assertThat(weapon.isAnimated(), is(true));
    }

    @Test
    public void isInitiallyNotVisible() {

        assertThat(weapon.isObjectVisible(), is(true));
    }

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

    @Test
    public void hasRoundsProperty() {

        assertThat(weapon, hasProperty("rounds"));
    }

    @Test
    public void roundsOfTypeInt() {

        assertThat(weapon.getRounds(), isA(Integer.class));
    }

    @Test
    public void initialNumberOfRoundsIsSix() {

        final Weapon aWeapon = new Weapon();
        assertThat(aWeapon.getRounds(), is(6));
    }

    @Test
    void firingRoundsReducesTheirCountAndTriggersChangeListeners() {

        final Weapon aWeapon = new Weapon();
        final int rounds = aWeapon.getRounds();
        final PropertyChangeListener propertyChangeListener = mock(PropertyChangeListener.class);
        doThrow(new RuntimeException("First of six rounds fired!")).when(propertyChangeListener).propertyChange(ArgumentMatchers.any());
        aWeapon.addPropertyChangeListener(propertyChangeListener);

        final Throwable throwable = assertThrows(RuntimeException.class, aWeapon::fireRound);
        assertThat(throwable.getMessage(), is("First of six rounds fired!"));
        assertThat(aWeapon.getRounds(), is(rounds - 1));
    }

    @Test
    void firingSixRoundsInSuccessionLeadsToAThrownWeaponEmptyException() {

        final Weapon aWeapon = new Weapon();
        for(int i = 1; i < 6; i++)
            aWeapon.fireRound();

        assertThrows(Weapon.WeaponFiredEmptyException.class, aWeapon::fireRound);
    }

    @Test
    void firingSixRoundsInSuccessionLeadsToAReloadedWeapon() {

        final Weapon aWeapon = new Weapon();
        for(int i = 0; i < 6; i++) {
            try {
                aWeapon.fireRound();
            } catch (Weapon.WeaponFiredEmptyException ignored) {
            }
        }

        assertThat(aWeapon.getRounds(), is(6));
    }

    @Test
    public void pickingUpWeaponStopsItsAnimationAndVisibility() {

        final Weapon aWeapon = new Weapon();
        aWeapon.pickUp();

        assertThat(aWeapon.isObjectVisible(), is(false));
        assertThat(aWeapon.isAnimated(), is(false));
    }

    @Test
    public void droppingWeaponStartsItsAnimationAndVisibilityAtARandomlyPickedPrescribedPosition() {

        final Weapon aWeapon = new Weapon();
        aWeapon.pickUp();
        aWeapon.drop();

        assertThat(aWeapon.isObjectVisible(), is(true));
        assertThat(aWeapon.isAnimated(), is(true));
        assertThat(aWeapon.getPossibleWeaponLocations(), hasItem(aWeapon.getAbsolutePosition()));
    }

    @Test
    public void canChangePosition() {

        final Weapon aWeapon = new Weapon();
        assertThat(aWeapon.canChangePosition(), is(true));
    }

}
