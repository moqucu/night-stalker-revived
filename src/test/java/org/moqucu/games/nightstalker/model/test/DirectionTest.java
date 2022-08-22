package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class DirectionTest {

    @Test
    public void presenceOfUpInstance() {

        assertThat(Direction.valueOf("Up"), is(notNullValue()));
        assertThat(Direction.valueOf("Up"), is(Direction.Up));
    }

    @Test
    public void presenceOfDownInstance() {

        assertThat(Direction.valueOf("Down"), is(notNullValue()));
        assertThat(Direction.valueOf("Down"), is(Direction.Down));
    }

    @Test
    public void presenceOfLeftInstance() {

        assertThat(Direction.valueOf("Left"), is(notNullValue()));
        assertThat(Direction.valueOf("Left"), is(Direction.Left));
    }

    @Test
    public void presenceOfRightInstance() {

        assertThat(Direction.valueOf("Right"), is(notNullValue()));
        assertThat(Direction.valueOf("Right"), is(Direction.Right));
    }

    @Test
    public void presenceOfUndefinedInstance() {

        assertThat(Direction.valueOf("Undefined"), is(notNullValue()));
        assertThat(Direction.valueOf("Undefined"), is(Direction.Undefined));
    }
}
