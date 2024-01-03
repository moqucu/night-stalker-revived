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
    }

    private void setNightStalkerModel(NightStalker model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof NightStalker))
            throw new RuntimeException("Game object needs to be of class NightStalker!");

        setNightStalkerModel((NightStalker)gameObject);
    }
}
