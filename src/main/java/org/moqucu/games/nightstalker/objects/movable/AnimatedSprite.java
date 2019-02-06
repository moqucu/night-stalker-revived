package org.moqucu.games.nightstalker.objects.movable;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.moqucu.games.nightstalker.objects.Sprite;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    @Singular
    protected List<Image> frames = new LinkedList<>();

    protected AnimatedSprite(Coordinates currentCoordinates) {

        super(currentCoordinates);
    }

    protected Image getFrame(double deltaTime) {

        int index = (int)((deltaTime % (frames.size() * frameDuration)) / frameDuration);
        return frames.get(index);
    }
}
