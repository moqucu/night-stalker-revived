package org.moqucu.games.nightstalker.view.test;

import javafx.scene.layout.TilePane;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.view.MazeBackgroundTilePane;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MazeBackgroundTilePaneTest {

    @Test
    public void mazeBackgroundTilePaneExistsAsInstantiableClass() {

        assertThat(new MazeBackgroundTilePane(), is(notNullValue()));
    }

    @Test
    public void mazeBackgroundTilePaneOfTypeTilePane() {

        assertThat(new MazeBackgroundTilePane(), isA(TilePane.class));
    }
}
