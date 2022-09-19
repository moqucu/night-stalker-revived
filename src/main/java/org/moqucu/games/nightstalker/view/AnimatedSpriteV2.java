package org.moqucu.games.nightstalker.view;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.AnimatedObject;

public abstract class AnimatedSpriteV2 extends SpriteV2 {

    @Getter
    private AnimatedObject model = new AnimatedObject() {
    };

    public void setModel(AnimatedObject model) {

        super.setModel(model);
        this.model = model;
    }
}
