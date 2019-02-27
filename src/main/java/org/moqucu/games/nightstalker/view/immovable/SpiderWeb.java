package org.moqucu.games.nightstalker.view.immovable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.view.Sprite;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpiderWeb extends Sprite {

    public SpiderWeb() {

        super(Point2D.ZERO);
        setImage(new Image(translate("images/spider-web.png")));
    }

    @Override
    public boolean intersects(Sprite sprite) {

        return false;
    }
}
