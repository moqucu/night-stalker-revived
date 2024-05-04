package org.moqucu.games.nightstalker.view.object;

import javafx.scene.media.AudioClip;
import lombok.Getter;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSprite;

import java.util.Objects;

public class WeaponSprite extends AnimatedSprite {

    @Getter
    private WeaponBulletSprite weaponBulletSprite;

    private final AudioClip roundFiredAudio = new AudioClip(
            Objects.requireNonNull(getClass().getResource("/sounds/shoot.wav")).toString()
    );

    public WeaponSprite() {

        super(new Weapon());
        roundFiredAudio.setVolume(0.5f);
        roundFiredAudio.setCycleCount(1);

        getModel().addPropertyChangeListener(
                evt -> {
                    if (evt.getSource().equals(getModel()) && evt.getPropertyName().equals("rounds"))
                        roundFiredAudio.play();
                }
        );
    }

    public void setWeaponBulletSprite(WeaponBulletSprite weaponBulletSprite) {

        this.weaponBulletSprite = weaponBulletSprite;
        ((Weapon)getModel()).setBullet(weaponBulletSprite.getModel());
    }
}
