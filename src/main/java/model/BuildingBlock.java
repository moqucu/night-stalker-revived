package model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class BuildingBlock {

    public enum Type {
        Maze,
        Bunker,
        BunkerSolid,
        SpiderWeb,
        Wall,
        Path
    }

    private Type type;

    @Singular
    private List<Position> positions;

    private Image image;
}
