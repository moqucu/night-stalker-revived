package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.RelativePosition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RelativePositionTest {

    private final RelativePosition relativePosition = new RelativePosition(0, 0);

    @Test
    public void hasXProperty() {

        assertThat(relativePosition, hasProperty("x"));
    }

    @Test
    public void xIsOfTypeInteger() {

        assertThat(relativePosition.getX(), instanceOf(Integer.class));
    }

    @Test
    public void hasYProperty() {

        assertThat(relativePosition, hasProperty("y"));
    }

    @Test
    public void yIsOfTypeInteger() {

        assertThat(relativePosition.getY(), instanceOf(Integer.class));
    }
}
