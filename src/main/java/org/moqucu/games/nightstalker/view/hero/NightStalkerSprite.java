package org.moqucu.games.nightstalker.view.hero;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.hero.NightStalker;
import org.moqucu.games.nightstalker.view.MovableSprite;

@Getter
public class NightStalkerSprite extends MovableSprite {

    private NightStalker model;

    public NightStalkerSprite() {

        super(new NightStalker());
        model = (NightStalker) super.getModel();
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
