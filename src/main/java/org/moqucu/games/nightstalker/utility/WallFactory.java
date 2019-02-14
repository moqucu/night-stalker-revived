package org.moqucu.games.nightstalker.utility;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.immovable.Wall;

import java.util.ArrayList;
import java.util.List;

public class WallFactory {

    @Data
    @Builder
    public static class WallFactoryConfiguration {

        private Image image;

        @Singular
        private List<Sprite.Position> initialPositions;
    }

    public static List<Wall> createWalls(WallFactoryConfiguration configuration) {

        List<Wall> walls = new ArrayList<>();

        configuration.getInitialPositions().forEach(
                initialPosition -> walls.add(new Wall(configuration.getImage(), initialPosition))
        );

        return walls;
    }
}
