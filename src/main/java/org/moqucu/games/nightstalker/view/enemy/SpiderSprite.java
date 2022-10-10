package org.moqucu.games.nightstalker.view.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.enemy.Spider;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;

public class SpiderSprite extends MovableSpriteV2 {

    @Getter
    private Spider model;

    public SpiderSprite() {

        super(new org.moqucu.games.nightstalker.model.enemy.Spider());
        model = (Spider) super.getModel();
    }

    public void setModel(Spider model) {

        super.setModel(model);
        this.model = model;
    }
}
