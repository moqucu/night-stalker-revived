package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OuterRingTest extends MazeAlgorithmImplTest {

    private final OuterRing outerRing = new OuterRing();

    @Test
    public void from_32_00_Up_To_32_32_Down() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 0.0), Direction.Up)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Down))
        );
    }

    @Test
    public void from_32_32_Down_To_00_32_Left() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Down)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(0.0, 32.0), Direction.Left))
        );
    }

    @Test
    public void from_00_32_Left_To_32_32_Right() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(0.0, 32.0), Direction.Left)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Right))
        );
    }

    @Test
    public void from_32_32_Right_To_32_64_Down() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 64.0), Direction.Down))
        );
    }

    @Test
    public void from_32_64_Down_To_00_64_Left() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 64.0), Direction.Down)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(0.0, 64.0), Direction.Left))
        );
    }

    @Test
    public void from_32_32_Up_To_64_32_Right() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Up)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(64.0, 32.0), Direction.Right))
        );
    }

    @Test
    public void from_64_32_Right_To_96_32_Right() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(64.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Right))
        );
    }

    @Test
    public void from_96_32_Right_To_96_00_Up() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(96.0, 00.0), Direction.Up))
        );
    }

    @Test
    public void from_64_32_Left_To_32_32_Left() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(64.0, 32.0), Direction.Left)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Left))
        );
    }

    @Test
    public void from_32_32_Left_To_32_00_Up() {

        assertThat(
                outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Left)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 0.0), Direction.Up))
        );
    }

    @Test
    public void testException() {

        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> outerRing.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Undefined)
                )

        );
        assertThat(throwable.getMessage(), is("Undefined is an unacceptable direction!"));
    }
}
