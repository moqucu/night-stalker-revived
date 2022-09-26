package org.moqucu.games.nightstalker.view.background;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.SpriteV2;

@Data
@EqualsAndHashCode(callSuper = true)
public class WebSprite extends SpriteV2 {

    public WebSprite() {

        super();
        getModel().setImageMapFileName("/images/web.png");
    }
}
