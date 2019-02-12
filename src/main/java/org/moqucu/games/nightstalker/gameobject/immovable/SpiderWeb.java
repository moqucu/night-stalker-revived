package org.moqucu.games.nightstalker.gameobject.immovable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moqucu.games.nightstalker.gameobject.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpiderWeb extends Sprite implements Renderable {

    public SpiderWeb(Image image, Position initialPosition) {

        super(initialPosition);
        setInitialImage(image);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }

    @Override
    public boolean intersects(Sprite sprite) {

        return false;
    }
}
