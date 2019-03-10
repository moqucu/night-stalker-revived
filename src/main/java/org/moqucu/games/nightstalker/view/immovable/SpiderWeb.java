package org.moqucu.games.nightstalker.view.immovable;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpiderWeb extends Sprite {

    public SpiderWeb() {

        setImage(new Image(translate("images/spider-web.png")));
    }
}
