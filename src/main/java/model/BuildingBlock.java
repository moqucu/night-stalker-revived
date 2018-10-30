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
        Bunker,
        BunkerSolid,
        BunkerHalfSolid,
        SpiderWeb,
        Wall
    }

    private Type type;

    @Singular
    private List<Position> positions;

    private Image image;
}
