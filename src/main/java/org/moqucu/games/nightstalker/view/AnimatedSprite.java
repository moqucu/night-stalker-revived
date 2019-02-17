package org.moqucu.games.nightstalker.view;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    @Singular
    protected List<Rectangle2D> frames = new LinkedList<>();

    protected AnimatedSprite(Point2D currentCoordinates) {

        super(currentCoordinates);
    }

    protected Rectangle2D getViewPortForFrame(double deltaTime) {

        int index = (int)((deltaTime % (frames.size() * frameDuration)) / frameDuration);
        return frames.get(index);
    }
}
