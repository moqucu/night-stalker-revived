package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.AbsPosAndDirection;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.Direction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;

public class AbsPosAndDirectionTest {

    private final AbsPosAndDirection absPosAndDirection = new AbsPosAndDirection(
            new AbsolutePosition(32.0, 32.0),
            Direction.Left
    );

    @Test
    public void hasPropertyAbsPos() {

        assertThat(absPosAndDirection, hasProperty("absolutePosition"));
    }

    @Test
    public void absPosPropertyOfTypeAbsolutePosition() {

        assertThat(absPosAndDirection.absolutePosition(), isA(AbsolutePosition.class));
    }

    @Test
    public void hasPropertyDirection() {

        assertThat(absPosAndDirection, hasProperty("direction"));
    }

    @Test
    public void directionPropertyOfTypeDirection() {

        assertThat(absPosAndDirection.direction(), isA(Direction.class));
    }
}
