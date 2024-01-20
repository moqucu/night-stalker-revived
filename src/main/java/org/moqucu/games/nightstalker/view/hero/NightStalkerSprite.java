package org.moqucu.games.nightstalker.view.hero;

import javafx.scene.media.AudioClip;
import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.hero.NightStalker;
import org.moqucu.games.nightstalker.view.MovableSprite;

import java.util.Objects;

@Getter
public class NightStalkerSprite extends MovableSprite {

    private NightStalker model;

    private final AudioClip pickUpGunAudio = new AudioClip(
            Objects.requireNonNull(getClass().getResource("/sounds/pickupgun.wav")).toString()
    );

    public NightStalkerSprite() {

        super(new NightStalker());

        pickUpGunAudio.setVolume(0.5f);
        pickUpGunAudio.setCycleCount(1);

        model = (NightStalker) super.getModel();
        model.addPropertyChangeListener(
                evt -> {

                    if (evt.getPropertyName().equals("weapon"))
                        pickUpGunAudio.play();
                }
        );

        setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case LEFT -> model.setLeftPressed(true);
                        case RIGHT -> model.setRightPressed(true);
                        case UP -> model.setUpPressed(true);
                        case DOWN -> model.setDownPressed(true);
                    }
                }
        );
        setOnKeyReleased(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case LEFT -> model.setLeftPressed(false);
                        case RIGHT -> model.setRightPressed(false);
                        case UP -> model.setUpPressed(false);
                        case DOWN -> model.setDownPressed(false);
                    }
                }
        );
    }

    private void setNightStalkerModel(NightStalker model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof NightStalker))
            throw new RuntimeException("Game object needs to be of class NightStalker!");

        setNightStalkerModel((NightStalker) gameObject);
    }
}
