package org.moqucu.games.nightstalker.view.background;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.background.Bunker;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

@Getter
public class BunkerSprite extends DisplayableSprite {

    private Bunker model;

    public BunkerSprite() {

        super(new Bunker());
        model = (Bunker) super.getModel();
    }

    private void setBunkerModel(Bunker model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof Bunker))
            throw new RuntimeException("Game object needs to be of class Bunker!");

        setBunkerModel((Bunker) gameObject);
    }
}
