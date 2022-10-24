package org.moqucu.games.nightstalker.view.background;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class WebSprite extends Sprite {

    public WebSprite() {

        super();
        getModel().setImageMapFileName("/images/web.png");
    }
}
