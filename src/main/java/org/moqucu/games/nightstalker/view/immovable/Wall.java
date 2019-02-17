package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.view.Sprite;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@ToString(callSuper = true)
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class Wall extends Sprite {

    public Wall() {

        super(Point2D.ZERO);
        setImage(new Image(translate("images/wall.png")));
    }
}
