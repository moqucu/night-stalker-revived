package org.moqucu.games.nightstalker.view;

import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.Splash;

public class SplashSprite extends DisplayableSprite {

    private Splash model;

    public SplashSprite() {

        super(new Splash());

        model = (Splash) super.getModel();
    }

    private void setSplashModel(Splash model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof Splash))
            throw new RuntimeException("Game object needs to be of class Splash!");

        setSplashModel((Splash) gameObject);
    }
}
