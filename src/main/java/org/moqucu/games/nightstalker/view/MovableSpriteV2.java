package org.moqucu.games.nightstalker.view;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.MovableObject;

public class MovableSpriteV2 extends AnimatedSpriteV2 {

    @Getter
    private MovableObject model;

    public MovableSpriteV2() {

        super(new MovableObject(){});
        model = (MovableObject) super.getModel();
    }

    public MovableSpriteV2(MovableObject model) {

        super(model);
        this.model = model;
    }

    public void setModel(MovableObject model) {

        super.setModel(model);
        this.model = model;
    }
}
