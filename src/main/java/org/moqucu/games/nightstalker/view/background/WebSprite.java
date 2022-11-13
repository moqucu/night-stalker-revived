package org.moqucu.games.nightstalker.view.background;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class WebSprite extends DisplayableSprite {

    public WebSprite() {

        super();
        getModel().setImageMapFileName("/images/web.png");
    }
}
