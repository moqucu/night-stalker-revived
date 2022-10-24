package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSpriteV2;
import org.moqucu.games.nightstalker.view.object.WeaponSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

public class WeaponSpriteTest {

    /*

        enum States {TossedAway, ReappearedOnTheGround, PickedUp}

    enum Events {reappear, pickUp, tossAway}

    private Map<States, Indices> frameBoundaries = Map.of(
            States.TossedAway, Indices.builder().lower(0).upper(0).build(),
            States.ReappearedOnTheGround, Indices.builder().lower(0).upper(1).build(),
            States.PickedUp, Indices.builder().lower(0).upper(0).build()
    );
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final IntegerProperty numberOfRounds = new SimpleIntegerProperty(6);

    private AudioClip pickUpGunSound = new AudioClip(
            Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/pickupgun.wav").toString()
    );

    private AudioClip shootSound
            = new AudioClip(Maze.class.getResource("/org/moqucu/games/nightstalker/sounds/shoot.wav").toString());

     */

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
