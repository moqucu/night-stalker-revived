package model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
public class BuildingBlock {

    public enum Type {
        Maze,
        Bunker,
        SpiderWeb,
        Wall,
        Path
    }

    private Type type;

    @Singular
    private List<Position> positions;

    private Image image;
}
