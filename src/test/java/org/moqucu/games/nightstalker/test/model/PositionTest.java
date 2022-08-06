package org.moqucu.games.nightstalker.test.model;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Position;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;
import static org.moqucu.games.nightstalker.model.Position.MAX_X;
import static org.moqucu.games.nightstalker.model.Position.MAX_Y;


public class PositionTest {

    private final Position position = new Position();

    @Test
    public void hasXProperty() {

        assertThat(position, hasProperty("x"));
    }

    @Test
    public void hasYProperty() {

        assertThat(position, hasProperty("y"));
    }

    @Test
    public void xPositionCannotBeBelowZero() {

        position.setX(4.0);
        position.addToX(-5.0);
        assertThat(position.getX(), is(0.0));
    }

    @Test
    public void xPositionCannotBeAboveMaximum() {

        position.setX(MAX_X - 4.0);
        position.addToX(5.0);
        assertThat(position.getX(), is(MAX_X));
    }

    @Test
    public void yPositionCannotBeBelowZero() {

        position.setY(2.0);
        position.addToY(-8.0);
        assertThat(position.getY(), is(0.0));
    }

    @Test
    public void yPositionCannotBeAboveMaximum() {

        position.setY(MAX_Y - 18.0);
        position.addToY(20.0);
        assertThat(position.getY(), is(MAX_Y));
    }
}
