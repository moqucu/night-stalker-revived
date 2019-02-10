package org.moqucu.games.nightstalker.objects.immovable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moqucu.games.nightstalker.objects.Renderable;
import org.moqucu.games.nightstalker.objects.Sprite;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wall extends Sprite implements Renderable {

    public Wall(Image image, Position initialPosition) {

        super(initialPosition);
        setInitialImage(image);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.drawImage(getInitialImage(), getCurrentCoordinates().getX(), getCurrentCoordinates().getY());
    }
}
