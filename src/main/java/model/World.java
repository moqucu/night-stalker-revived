package model;

import lombok.Data;

@Data
class World {

    enum BuildingBlock {
        Maze,
        Bunker,
        SpiderWeb,
        Wall,
        Path
    }

    private BuildingBlock[][] maze = new BuildingBlock[12][24];
}
