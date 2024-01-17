package org.moqucu.games.nightstalker.view.object;

import javafx.scene.media.AudioClip;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.AnimatedSprite;

import java.util.Objects;

public class WeaponSprite extends AnimatedSprite {

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
}
