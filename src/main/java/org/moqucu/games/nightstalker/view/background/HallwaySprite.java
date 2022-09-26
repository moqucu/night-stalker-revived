package org.moqucu.games.nightstalker.view.background;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.view.SpriteV2;

@Data
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class HallwaySprite extends SpriteV2 {

    public HallwaySprite() {

        super();
        getModel().setImageMapFileName("/images/hallway.png");
    }
}
