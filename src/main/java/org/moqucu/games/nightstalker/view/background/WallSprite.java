package org.moqucu.games.nightstalker.view.background;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.background.Wall;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

@Getter
public class WallSprite extends DisplayableSprite {

    private Wall model;


    public WallSprite() {

        super(new Wall());
        model = (Wall) super.getModel();
        setSmooth(false);
    }

    private void setWallModel(Wall model) {

        super.setModel(model);
        this.model = model;
    }
    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof Wall))
            throw new RuntimeException("Game object needs to be of class Wall!");

        setWallModel((Wall)gameObject);
    }
}
