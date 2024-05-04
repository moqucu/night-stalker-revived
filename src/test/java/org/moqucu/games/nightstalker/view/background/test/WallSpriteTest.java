package org.moqucu.games.nightstalker.view.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.background.Wall;
import org.moqucu.games.nightstalker.view.DisplayableSprite;
import org.moqucu.games.nightstalker.view.background.WallSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WallSpriteTest {

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(new WallSprite(), isA(DisplayableSprite.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(new WallSprite(), hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeWall() {

        assertThat((new WallSprite()).getModel(), isA(Wall.class));
    }

    @Test
    public void imageMapFileNameIsCorrect() {

        assertThat((new WallSprite()).getModel().getImageMapFileName(), is("/images/wall.png"));
    }

    @Test
    public void settingAModelWorks() {

        final WallSprite wallSprite = new WallSprite();
        final Wall wall = new Wall();
        wallSprite.setModel(wall);

        assertThat(wallSprite.getModel(), is(wall));
    }
}
