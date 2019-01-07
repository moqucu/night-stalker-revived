package model;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Direction.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {

    protected Direction direction = Right;

    @Singular
    protected Map<Direction, List<Image>> frames = new HashMap<>();

    protected double frameDuration;

    AnimatedSprite(Coordinates currentCoordinates) {

        super(currentCoordinates);

        frames.put(Up, new ArrayList<>());
        frames.put(Right, new ArrayList<>());
        frames.put(Down, new ArrayList<>());
        frames.put(Left, new ArrayList<>());
    }

    Image getFrame(double deltaTime) {

        int index = (int)((deltaTime % (frames.get(direction).size() * frameDuration)) / frameDuration);
        return frames.get(direction).get(index);
    }
}
