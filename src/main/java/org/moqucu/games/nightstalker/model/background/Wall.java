package org.moqucu.games.nightstalker.model.background;

import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.DisplayableObject;

@Log4j2
public class Wall extends DisplayableObject {

    public Wall() {

        super();
        setImageMapFileName("/images/wall.png");
    }
}
