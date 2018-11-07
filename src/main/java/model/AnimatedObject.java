package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimatedObject {
    // assumes animation loops,
    //  each image displays for equal time
    protected List<Image> frames = new ArrayList<>();
    protected double duration;

    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.size() * duration)) / duration);
        return frames.get(index);
    }
}
