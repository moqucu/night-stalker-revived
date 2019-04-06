package org.moqucu.games.nightstalker.sprite.background;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.sprite.Sprite;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class HallWay extends Sprite {

    public HallWay() {

        setImage(new Image(translate("images/hall-way.png")));
    }
}
