package org.moqucu.games.nightstalker.view;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.moqucu.games.nightstalker.view.Sprite;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    @Singular
    protected List<Image> frames = new LinkedList<>();

    protected AnimatedSprite(Point2D currentCoordinates) {

        super(currentCoordinates);
    }

    //todo: convert to viewport based function
    protected Image getFrame(double deltaTime) {

        int index = (int)((deltaTime % (frames.size() * frameDuration)) / frameDuration);
        return frames.get(index);
    }
}
