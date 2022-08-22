package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AbsolutePosition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.moqucu.games.nightstalker.model.AbsolutePosition.MAX_X;
import static org.moqucu.games.nightstalker.model.AbsolutePosition.MAX_Y;


public class AbsolutePositionTest {

    private final AbsolutePosition absolutePosition = new AbsolutePosition();

    @Test
    public void hasXProperty() {

        assertThat(absolutePosition, hasProperty("x"));
    }

    @Test
    public void xIsOfTypeDouble() {

        assertThat(absolutePosition.getX(), instanceOf(Double.class));
    }

    @Test
    public void hasYProperty() {

        assertThat(absolutePosition, hasProperty("y"));
    }

    @Test
    public void yIsOfTypeDouble() {

        assertThat(absolutePosition.getY(), instanceOf(Double.class));
    }

    @Test
    public void xPositionCannotBeBelowZero() {

        absolutePosition.setX(4.0);
        absolutePosition.addToX(-5.0);
        assertThat(absolutePosition.getX(), is(0.0));
    }

    @Test
    public void xPositionCannotBeAboveMaximum() {

        absolutePosition.setX(MAX_X - 4.0);
        absolutePosition.addToX(5.0);
        assertThat(absolutePosition.getX(), is(MAX_X));
    }

    @Test
    public void yPositionCannotBeBelowZero() {

        absolutePosition.setY(2.0);
        absolutePosition.addToY(-8.0);
        assertThat(absolutePosition.getY(), is(0.0));
    }

    @Test
    public void yPositionCannotBeAboveMaximum() {

        absolutePosition.setY(MAX_Y - 18.0);
        absolutePosition.addToY(20.0);
        assertThat(absolutePosition.getY(), is(MAX_Y));
    }
}
