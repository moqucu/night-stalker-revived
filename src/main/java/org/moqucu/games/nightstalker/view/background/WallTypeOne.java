package org.moqucu.games.nightstalker.view.background;

import org.moqucu.games.nightstalker.view.SpriteV2;

public class WallTypeOne extends SpriteV2 {

    public WallTypeOne() {

        super();
        getModel().setImageMapFileName("/org/moqucu/games/nightstalker/images/wall.png");
        getModel().setInitialImageIndex(0);
    }
}
