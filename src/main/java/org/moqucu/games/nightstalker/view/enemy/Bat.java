package org.moqucu.games.nightstalker.view.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.enemy.BatModel;
import org.moqucu.games.nightstalker.view.MovableSpriteV2;

public class Bat extends MovableSpriteV2 {

    @Getter
    private BatModel model;

    public Bat() {

        super(new BatModel());
        model = (BatModel) super.getModel();
    }

    public void setModel(BatModel model) {

        super.setModel(model);
        this.model = model;
    }
}
