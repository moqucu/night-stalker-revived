package model;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AnimatedSprite extends Sprite {
    // assumes animation loops,
    //  each image displays for equal time
    @Singular
    protected List<Image> frames = new ArrayList<>();

    protected double frameDuration;

    AnimatedSprite(Position initialPosition) {

        super(initialPosition);
    }

    Image getFrame(double time)
    {
        int index = (int)((time % (frames.size() * frameDuration)) / frameDuration);
        return frames.get(index);
    }
}
